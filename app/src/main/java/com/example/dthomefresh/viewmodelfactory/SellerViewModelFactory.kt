package com.example.dthomefresh.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dthomefresh.viewmodel.SellersViewModel

class SellersViewModelFactory(private val optionSelected: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SellersViewModel::class.java)) {
            return SellersViewModel(optionSelected) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}