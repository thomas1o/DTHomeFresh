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
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class SellersViewModel(optionSelected : Int) : ViewModel() {

    private val _options = MutableLiveData<MutableMap<Int, Boolean>>()
    val options: LiveData<MutableMap<Int, Boolean>>
        get() = _options

    private val _loadingAnimation = MutableLiveData<Boolean>()
    val loadingAnimation: LiveData<Boolean>
        get() = _loadingAnimation

    private val _setUpRecyclerView = MutableLiveData<Boolean>()
    val setUpRecyclerView: LiveData<Boolean>
        get() = _setUpRecyclerView

    private val _sellersList = MutableLiveData<List<Seller>>()
    val sellersList: LiveData<List<Seller>>
        get() = _sellersList

    init {
        when(optionSelected) {
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
    private fun setTrue(elNum : Int) {
        val currentOptions = _options.value
        currentOptions?.set(elNum, true)
        _options.value = currentOptions!!
    }

    private suspend fun fetchSellersFromFirebase() : List<Seller> {
        return withContext(Dispatchers.IO) {
            suspendCoroutine {continuation ->

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

                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // TODO- Handle error
                        continuation.resumeWithException(databaseError.toException())
                    }
                })

            }
        }
    }

    fun readAllSellers() {
        viewModelScope.launch {
            val sellerList = fetchSellersFromFirebase()
            _sellersList.postValue(sellerList)              //postValue ensures that operation happens in bg thread
            _setUpRecyclerView.postValue(true)        //postValue ensures that operation happens in bg thread
            stopLoadingAnimation()
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
}