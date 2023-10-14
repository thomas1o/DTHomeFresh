package com.example.dthomefresh.sellers

import android.text.BoringLayout
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SellersViewModel : ViewModel() {

    private val _options = MutableLiveData<MutableMap<Int, Boolean>>()
    val options: LiveData<MutableMap<Int, Boolean>>
            get() = _options
    init {
        setAllFalse()
    }
    fun onFood() {
        setAllFalse()
        setTrue(0)
    }
    fun onClothes() {
        setAllFalse()
        setTrue(1)
    }
    fun onHomemadeItems() {
        setAllFalse()
        setTrue(2)
    }
    private fun setAllFalse() {
        _options.value = mutableMapOf(
            0 to false,
            1 to false,
            2 to false
        )
    }
    private fun setTrue(elNum : Int) {
        val currentOptions = _options.value
        currentOptions?.set(elNum, true)
        _options.value = currentOptions!!
    }
}