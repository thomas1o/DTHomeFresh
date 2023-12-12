package com.example.dthomefresh.utils

import java.util.regex.Pattern

class Validator {

    fun passwordValidator(password: String): Boolean {
        return !(password.isEmpty() || password.length < 6 || !hasCap(password) ||
                !hasNum(password) || !hasSpecial(password))
    }

    fun usernameValidator(userName: String): Boolean {
        return !(userName.isEmpty() || userName.startsWith(" ") || userName.endsWith(" "))
    }

    private fun hasCap(password: String): Boolean {
        val pattern = Pattern.compile("[A-Z]")
        val matcherCap = pattern.matcher(password)
        return matcherCap.find()
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