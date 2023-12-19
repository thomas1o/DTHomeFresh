package com.example.dthomefresh.ui

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentMapBottomSheetBinding
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale

class MapBottomSheet : Fragment() {

    lateinit var binding: FragmentMapBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_bottom_sheet, container, false)

        binding.btEnterAddress.setOnClickListener {
            val addressBottomSheetFragment = AddressBottomSheetFragment()
            addressBottomSheetFragment.show(parentFragmentManager, "AddressBottomSheetFragment")
        }

        return binding.root
    }

//    private fun displayPlaceInformation(latLng: LatLng) {
//        val geocoder = Geocoder(requireContext(), Locale.getDefault())
//        try {
//            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//            if (addresses != null) {
//                if (addresses.isNotEmpty()) {
//                    val address = addresses[0]
//                    // Get the name of the place or address and display it
//                    val placeName = address.getAddressLine(0)
////                    binding.placeName.text = placeName
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }

}