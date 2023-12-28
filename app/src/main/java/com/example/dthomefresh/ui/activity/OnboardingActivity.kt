package com.example.dthomefresh.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding)

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        binding.btGetStarted.setOnClickListener {
            startActivity(Intent(this@OnboardingActivity, MainActivity::class.java))
            vibrator.vibrate(100)
            finish()
        }

    }
}