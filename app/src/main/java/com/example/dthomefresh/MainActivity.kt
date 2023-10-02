package com.example.dthomefresh

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.get_started_button)
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        button.setOnClickListener {
            vibrator.vibrate(100)
            val intent = Intent(this, CategoriesActivty::class.java)
            startActivity(intent)
            finish()
        }

    }
}