package com.example.dthomefresh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class MapAndBottomSheetViewModel: ViewModel() {

    private val _address = MutableLiveData<String>()
    val address: LiveData<String>
        get() = _address

    private val _addressCoordinates = MutableLiveData<LatLng>()
    val addressCoordinates: LiveData<LatLng>
        get() = _addressCoordinates

    private val _animationOn = MutableLiveData<Boolean>()
    val animationOn: LiveData<Boolean>
        get() = _animationOn

    fun setAddress(address: String) {
        _address.postValue(address)
    }
    fun setAddressCoordinates(addressCoordinates: LatLng) {
        _addressCoordinates.postValue(addressCoordinates)
    }
    fun startAnimation() {
        _animationOn.value = true
    }

    fun stopAnimation() {
        _animationOn.value = false
    }
}