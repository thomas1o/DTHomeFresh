package com.example.dthomefresh.sellers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SellersViewModelFactory(private val optionSelected: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SellersViewModel::class.java)) {
            return SellersViewModel(optionSelected) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}