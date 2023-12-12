package com.example.dthomefresh.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openMail(context: Context, mail: String) {

    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("mailto:$mail")
    }

    context.startActivity(intent)
}