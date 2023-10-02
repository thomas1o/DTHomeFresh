package com.example.dthomefresh

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.ImageButton

class CategoriesActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        val menuButton = findViewById<ImageButton>(R.id.menu_button)
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        menuButton.setOnClickListener{
            vibrator.vibrate(100)
        }
    }
}