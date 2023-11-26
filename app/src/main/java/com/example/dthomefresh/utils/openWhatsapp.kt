package com.example.dthomefresh.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openWhatsapp(context: Context, phoneNumber: String) {

    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
    }

    // TODO Verify that WhatsApp is installed on the device before opening it

    context.startActivity(intent)
}