package com.example.dthomefresh.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openDialPad(context: Context, phoneNumber: String) {

    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }

    context.startActivity(intent)
}