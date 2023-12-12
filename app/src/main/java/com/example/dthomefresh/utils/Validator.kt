package com.example.dthomefresh.utils

import android.util.Patterns
import java.util.regex.Pattern

class Validator {

    fun emailValidator(email: String): String? {
        if(email.isEmpty())
            return "Email cannot be empty"
        else if (!isValidEmail(email))
            return "Not a valid email"
        else
            return null
    }

    fun passwordValidator(password: String): String? {
        if(password.isEmpty())
            return "Password cannot be empty"
        else if (password.length < 6)
            return "Should have at least 6 characters"
//        else if (!hasCap(password))
//            return "Should contain at least 1 capital, 1 special and 1 number"
//        else if (!hasNum(password))
//            return "Should contain at least 1 capital, 1 special and 1 number"
//        else if (!hasSpecial(password))
//            return "Should contain at least 1 capital, 1 special and 1 number"
        else
            return null
//
//        return !(password.isEmpty() || password.length < 6 || !hasCap(password) ||
//                !hasNum(password) || !hasSpecial(password))
    }

    fun rePasswordValidator(password: String, rePassword: String): String? {
        if(rePassword.isEmpty())
            return "Password cannot be empty"
        else if (password != rePassword)
            return "Does not match with the above"
        else
            return null
    }

    private fun hasCap(password: String): Boolean {
        val pattern = Pattern.compile("[A-Z]")
        val matcherCap = pattern.matcher(password)
        return matcherCap.find()
    }
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun hasNum(password: String): Boolean {
        val pattern = Pattern.compile("[0-9]")
        val matcherNum = pattern.matcher(password)
        return matcherNum.find()
    }

    private fun hasSpecial(password: String): Boolean {
        val pattern = Pattern.compile("[^A-Za-z0-9]")
        val matcherSpecial = pattern.matcher(password)
        return matcherSpecial.find()
    }

}