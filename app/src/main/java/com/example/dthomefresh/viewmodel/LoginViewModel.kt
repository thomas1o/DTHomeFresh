package com.example.dthomefresh.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dthomefresh.utils.ExceptionHandler.handleException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val email = MutableLiveData<String?>()
    private val password = MutableLiveData<String?>()

    private val _signInSuccess = MutableLiveData<Boolean>()
    val signInSuccess: LiveData<Boolean>
        get() = _signInSuccess

    private val _loginAnimation = MutableLiveData<Boolean>()
    val loginAnimation: LiveData<Boolean>
        get() = _loginAnimation

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        setEmail("")
        setPassword("")
    }

    private fun signIn(email: String, password: String) {
        uiScope.launch {
            signInWithFirebase(email, password)
        }
    }

    private suspend fun signInWithFirebase(email: String, password: String) {
        withContext(Dispatchers.IO) {
            email.let { nonNullEmail ->
                password.let { nonNullPassword ->
                    try {
                        auth.signInWithEmailAndPassword(nonNullEmail, nonNullPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    _signInSuccess.value = true
                                    Log.i("LoginViewModel", "Login successful")
                                } else {
                                    _signInSuccess.value = false
                                    val e = task.exception
                                    _signInSuccess.value = false
                                    displayError(e)
                                }
                            }
                    } catch (e: Exception) {
                        displayError(e)
                    }
                }
            }
        }
    }

    fun startLoginAnimation() {
        _loginAnimation.value = true
    }

    fun stopLoginAnimation() {
        _loginAnimation.value = false
    }

    fun setEmail(emailInput: String?) {
        email.value = emailInput
    }

    fun setPassword(passwordInput: String?) {
        password.value = passwordInput
    }

    fun startSignIn() {
        signIn(email.value ?: "", password.value ?: "")
    }

    fun displayError(e: Exception?) {
        _errorMessage.value = handleException(e)
    }

    override fun onCleared() {
        uiScope.cancel()
        super.onCleared()
    }

}