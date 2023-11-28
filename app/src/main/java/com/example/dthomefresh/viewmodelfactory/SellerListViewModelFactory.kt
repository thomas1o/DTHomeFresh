package com.example.dthomefresh.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dthomefresh.viewmodel.SellerListViewModel

class SellerListViewModelFactory(private val optionSelected: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SellerListViewModel::class.java)) {
            return SellerListViewModel(optionSelected) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}