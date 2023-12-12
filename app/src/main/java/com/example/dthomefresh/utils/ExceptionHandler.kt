package com.example.dthomefresh.utils

import android.accounts.NetworkErrorException
import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import java.util.concurrent.TimeoutException

object ExceptionHandler {
    fun handleException(exception: Exception?): String {
        val errorMessage = when (exception) {
            is FirebaseAuthException -> "Oops! There was an issue with your authentication."
            is FirebaseNetworkException -> "Please check your network."
            is NetworkErrorException -> "Please check your network."
            is TimeoutException -> "Taking too long to load. Please try again later."
            is FirebaseAuthUserCollisionException -> "Account already exists. Please log in."
            is FirebaseAuthWeakPasswordException -> "Weak password. Please use a stronger password."
            is FirebaseAuthInvalidCredentialsException -> "Invalid email format. Please enter a valid email."
            else -> "Something went wrong. Please try again."
        }
        Log.e("TAG", errorMessage)
        return errorMessage
    }
}
