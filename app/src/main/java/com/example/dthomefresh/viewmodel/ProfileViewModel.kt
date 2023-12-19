package com.example.dthomefresh.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dthomefresh.data.Seller
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProfileViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val currentUser = firebaseAuth.currentUser
    private val database = FirebaseDatabase.getInstance()

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _loginAnimation = MutableLiveData<Boolean>()
    val loginAnimation: LiveData<Boolean>
        get() = _loginAnimation

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        _email.value = currentUser?.email
    }

    fun writeNewUser(name: String, phoneNumber: String, address: String) {
        val seller = Seller(name, phoneNumber, address)
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        currentUser?.let { firebaseUser ->
            val uid = firebaseUser.uid
            // Note: Write user data to the Firebase Realtime Database with the UID as the key
            database.reference.child("Sellers").child(uid).setValue(seller)
        }
    }
}