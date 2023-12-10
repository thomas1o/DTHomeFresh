package com.example.dthomefresh.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dthomefresh.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel: ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private val REQ_ONE_TAP = 2
    private val TAG = "SignUpViewModel"
    private var showOneTapUI = true
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val email = MutableLiveData<String?>()
    private val password = MutableLiveData<String?>()

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean>
        get() = _signUpSuccess
    private val _signUpAnimation = MutableLiveData<Boolean>()
    val signUpAnimation: LiveData<Boolean>
        get() = _signUpAnimation
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        setEmail("")
        setPassword("")
    }

    private fun signUpWithEmail(email: String, password: String) {
        uiScope.launch {
            signUpWithEmailUsingFirebase(email, password)
        }
    }

    private suspend fun signUpWithEmailUsingFirebase(email: String, password: String) {
        withContext(Dispatchers.IO) {
            email.let { nonNullEmail ->
                password.let { nonNullPassword ->
                    try{
                        auth.createUserWithEmailAndPassword(nonNullEmail, nonNullPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    _signUpSuccess.value = true
                                    Log.i(TAG, "SignUp successful")
                                    Firebase.auth.signOut()
                                } else {
                                    _signUpSuccess.value = false
                                    val exception = task.exception
                                    handleException(exception)
                                    Log.i(TAG, "SignUp failed")
                                }
                            }
                    } catch (e: Exception) {
                        handleException(e)
                    }
                }
            }
        }
    }

    fun handleException(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthUserCollisionException -> "Account already exists. Please log in."
            is FirebaseAuthWeakPasswordException -> "Weak password. Please use a stronger password."
            is FirebaseAuthInvalidCredentialsException -> "Invalid email format. Please enter a valid email."
            else -> "Something went wrong. Please Try Again."
        }
        _errorMessage.value = errorMessage
        Log.e("SignUpViewModel", errorMessage)
        _signUpSuccess.value = false
    }


    fun setEmail(emailInput: String?) {
        email.value = emailInput
    }
    fun setPassword(passwordInput: String?) {
        password.value = passwordInput
    }
    fun startLoginAnimation() {
        _signUpAnimation.value = true
    }
    fun stopLoginAnimation() {
        _signUpAnimation.value = false
    }
    fun startSignUp() {
        signUpWithEmail(this.email.value ?: "", this.password.value ?: "")
    }

    override fun onCleared() {
        uiScope.cancel()
        super.onCleared()
    }
}