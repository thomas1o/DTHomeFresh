package com.example.dthomefresh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dthomefresh.data.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddressBottomSheetViewModel: ViewModel() {

    private val database = FirebaseDatabase.getInstance()

    private val _loadingDone = MutableLiveData<Boolean>()
    val loadingDone: LiveData<Boolean>
        get() = _loadingDone

    init{
        onLoadingDone()
    }

    fun onLoadingDone() {
        _loadingDone.value = true
    }

    fun onLoading() {
        _loadingDone.value = false
    }

    fun writeNewUser(name: String, phoneNumber: String, address: String, nearbyLandmark: String) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val uid = currentUser?.uid
        val seller = uid?.let { Seller(it, name, phoneNumber, address, nearbyLandmark) }

        currentUser?.let {
            // Note: Write user data to the Firebase Realtime Database with the UID as the key
            if (uid != null) {
                database.reference.child("Sellers").child(uid).setValue(seller)
            }
        }
    }

}