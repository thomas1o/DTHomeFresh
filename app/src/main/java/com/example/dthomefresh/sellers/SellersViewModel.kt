package com.example.dthomefresh.sellers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SellersViewModel(optionSelected : Int) : ViewModel() {

    private val _options = MutableLiveData<MutableMap<Int, Boolean>>()
    val options: LiveData<MutableMap<Int, Boolean>>
            get() = _options
    init {
        when(optionSelected) {
            0 -> onFood()
            1 -> onClothes()
            2 -> onHomemadeItems()
        }
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
        val currentOptions = _options.value ?: mutableMapOf()
        for (key in 0..2) {
            currentOptions[key] = false
        }
        _options.value = currentOptions
    }
    private fun setTrue(elNum : Int) {
        val currentOptions = _options.value
        currentOptions?.set(elNum, true)
        _options.value = currentOptions!!
    }
}