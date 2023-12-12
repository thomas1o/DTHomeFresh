package com.example.dthomefresh.utils

import com.google.firebase.Firebase
import com.google.firebase.auth.auth

fun loggedInCheck(): Boolean {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    return currentUser != null
}