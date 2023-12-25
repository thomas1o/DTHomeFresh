package com.example.dthomefresh.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentMapBinding
import com.example.dthomefresh.viewmodel.MapAndBottomSheetViewModel
import com.example.dthomefresh.viewmodel.MapViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding
    private lateinit var viewModel: MapViewModel
    private lateinit var sharedViewModel: MapAndBottomSheetViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: LatLng
    private var isLocationEnabled = false
    private lateinit var googleMap: GoogleMap
    private lateinit var centerMarker: Marker
    private lateinit var currentLocationMarker: Marker

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { map ->
        googleMap = map

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
            return@OnMapReadyCallback
        }

        if (isLocationEnabled) {
            try {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = false
                fetchLocation()
            }catch (e: Exception) {
                Log.e("MapFragment", "${e.message}")
            }
            googleMap.setOnCameraMoveListener {
                try {
                    updateCenterMarkerPosition(googleMap.cameraPosition.target)
                    displayPlaceInformation(googleMap.cameraPosition.target)
                }catch (e: Exception) {
                    Log.e("MapFragment", "${e.message}")
                }
            }

            val mapCenter = googleMap.projection.visibleRegion.latLngBounds.center
            centerMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(mapCenter)
                    .title("Center Marker")
            )!!
        }
        else {
            requestLocationPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)

        viewModel = ViewModelProvider(this)[MapViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[MapAndBottomSheetViewModel::class.java]

        binding.btCurrentLocation.setOnClickListener {
            checkLocationSettings()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        checkLocationSettings()
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun displayPlaceInformation(latLng: LatLng) {
        val geocoder = Geocoder(requireContext(), Locale.getDefault())

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    val address = addresses[0]
                    val addressText = address.getAddressLine(0)

                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastLocationUpdateTimestamp > MIN_UPDATE_INTERVAL) {
                        withContext(Dispatchers.Main) {
                            sharedViewModel.startAnimation()
                            centerMarker.title = "Marker"
                            sharedViewModel.setAddress(addressText)
                            sharedViewModel.setAddressCoordinates(latLng)
                        }
                        lastLocationUpdateTimestamp = currentTime
                    }
                } else {
                    Log.d("MapFragment", "No address found for the provided location.")
                }
            } catch (e: IOException) {
                Log.e("MapFragment", "Error getting address: ${e.message}")
            }
        }
    }


    private fun updateCenterMarkerPosition(latLng: LatLng) {
        if (::centerMarker.isInitialized) {
            centerMarker.position = latLng
        } else {
            centerMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title("Center Marker")
            )!!
        }
    }

    private fun checkLocationSettings() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)

        val client: SettingsClient = LocationServices.getSettingsClient(requireContext())
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            // Location settings meet the requirements
            isLocationEnabled = true
            initializeMap()
            updateCenterMarker()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings do not meet the requirements, show dialog to enable location
                try {
                    startIntentSenderForResult(
                        exception.resolution.intentSender,
                        REQUEST_CHECK_SETTINGS,
                        null,
                        0,
                        0,
                        0,
                        null
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun updateCenterMarker() {
        if (::googleMap.isInitialized) {
            val mapCenter = googleMap.projection.visibleRegion.latLngBounds.center
            if (!::centerMarker.isInitialized || !centerMarker.isVisible) {
                centerMarker = googleMap.addMarker(
                    MarkerOptions()
                        .position(mapCenter)
                        .title("Center Marker")
                )!!
            } else {
                centerMarker.position = mapCenter
            }
        }
    }


    private fun fetchLocation() {
        Log.i("MapFragment", "fetchLocation called")

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = LatLng(location.latitude, location.longitude)
                if (isAdded && ::googleMap.isInitialized) {
                    googleMap.addMarker(
                        MarkerOptions().position(currentLocation).title("Current Location")
                    )
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14f))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                isLocationEnabled = true
                initializeMap()
                updateCenterMarker()
            }
        }
    }

    private fun initializeMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val REQUEST_CHECK_SETTINGS = 123
        private val MIN_UPDATE_INTERVAL = 2000L
        private var lastLocationUpdateTimestamp: Long = 0
    }
}
