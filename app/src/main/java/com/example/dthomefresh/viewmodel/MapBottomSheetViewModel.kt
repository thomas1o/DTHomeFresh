package com.example.dthomefresh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapBottomSheetViewModel: ViewModel() {

    private val _loadingDone = MutableLiveData<Boolean>()
    val loadingDone: LiveData<Boolean>
        get() = _loadingDone

    private val _placeName = MutableLiveData<String?>()
    val placeName: LiveData<String?>
        get() = _placeName

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

}