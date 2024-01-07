package com.example.dthomefresh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dthomefresh.data.Seller
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SellerListViewModel(optionSelected: Int) : ViewModel() {

    private val _options = MutableLiveData<MutableMap<Int, Boolean>>()
    val options: LiveData<MutableMap<Int, Boolean>>
        get() = _options

    private val _loadingAnimation = MutableLiveData<Boolean>()
    val loadingAnimation: LiveData<Boolean>
        get() = _loadingAnimation

    private val _refreshingAnimation = MutableLiveData<Boolean>()
    val refreshingAnimation: LiveData<Boolean>
        get() = _refreshingAnimation

    private val _setUpRecyclerView = MutableLiveData<Boolean>()
    val setUpRecyclerView: LiveData<Boolean>
        get() = _setUpRecyclerView

    private val _sellersList = MutableLiveData<List<Seller>>()
    val sellersList: LiveData<List<Seller>>
        get() = _sellersList

    private val _errorToast = MutableLiveData<String>()
    val errorToast: LiveData<String>
        get() = _errorToast

    init {
        when (optionSelected) {
            0 -> onFood()
            1 -> onClothes()
            2 -> onHomemadeItems()
        }
        startLoadingAnimation()
    }

    private fun setAllFalse() {
        val currentOptions = _options.value ?: mutableMapOf()
        for (key in 0..2) {
            currentOptions[key] = false
        }
        _options.value = currentOptions
        _setUpRecyclerView.value = false
    }

    private fun setTrue(elNum: Int) {
        val currentOptions = _options.value
        currentOptions?.set(elNum, true)
        _options.value = currentOptions!!
    }

    private suspend fun fetchSellersFromFirebase(): List<Seller> {
        startLoadingAnimation()
        return withContext(Dispatchers.IO) {
            try {
                suspendCoroutine { continuation ->
                    val database = FirebaseDatabase.getInstance()
                    database.reference.child("Sellers").addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val sellersList = mutableListOf<Seller>()

                            for (sellerSnapshot in dataSnapshot.children) {
                                val seller = sellerSnapshot.getValue(Seller::class.java)
                                seller?.let { sellersList.add(it) }
                            }

                            continuation.resume(sellersList)  //returns the seller list
                            stopLoadingAnimation()
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            val errorMessage = "Database Error: ${databaseError.message}"
                            _errorToast.postValue(errorMessage) // Post error message to LiveData
                            stopLoadingAnimation()
                            continuation.resume(emptyList())
                        }
                    })
                }
            } catch (e: Exception) {
                stopLoadingAnimation()
                val errorMessage = "An error occurred: ${e.message}"
                _errorToast.postValue(errorMessage)
                _errorToast.postValue("")
                emptyList() // or any default value
            }
        }
    }

    //FIXME(Bug)- it doesn't update when we click backspace
    fun filterSellerByNameAndAddress(searchText: String?) {
        viewModelScope.launch {
            val originalSellers = _sellersList.value.orEmpty()
            val filteredSellers = if (!searchText.isNullOrBlank()) {
                originalSellers.filter { seller ->
                    seller.brandName?.lowercase(Locale.ROOT)
                        ?.contains(searchText.lowercase(Locale.ROOT)) == true ||
                            seller.address?.lowercase(Locale.ROOT)
                                ?.contains(searchText.lowercase(Locale.ROOT)) == true
                }
            } else {
                originalSellers
            }
            _sellersList.postValue(filteredSellers)
        }
    }


    fun readAllSellers() {
        viewModelScope.launch {
            val sellerList = fetchSellersFromFirebase()
            _sellersList.postValue(sellerList)              //NOTE: postValue ensures that operation happens in bg thread
            _setUpRecyclerView.postValue(true)        //NOTE: postValue ensures that operation happens in bg thread
            stopLoadingAnimation()
            stopRefreshingAnimation()
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

    private fun startLoadingAnimation() {
        _loadingAnimation.value = true
    }

    private fun stopLoadingAnimation() {
        _loadingAnimation.value = false
    }

    private fun stopRefreshingAnimation() {
        _refreshingAnimation.value = false
    }
}