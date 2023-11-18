package com.example.dthomefresh.signup

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel: ViewModel() {

    private var auth: FirebaseAuth = Firebase.auth
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

    init {
        setEmail("")
        setPassword("")
    }

    private fun signUp(email: String, password: String) {
        uiScope.launch {
            signUpWithFirebase(email, password)
        }
    }

    private suspend fun signUpWithFirebase(email: String, password: String) {
        withContext(Dispatchers.IO) {
            email?.let { nonNullEmail ->
                password?.let { nonNullPassword ->
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener() { task ->
                            if (task.isSuccessful) {
                                _signUpSuccess.value = true
                                Log.i("SignUpViewModel", "SignUp successful")
                                Firebase.auth.signOut()
                            } else {
                                _signUpSuccess.value = false
                                Log.i("SignUpViewModel", "SignUp failed")
                            }
                        }
                }
            }
        }
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
        signUp(this.email.value ?: "", this.password.value ?: "")
    }
}