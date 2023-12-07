package com.example.dthomefresh.ui

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentOnboardingBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_onboarding, container, false
        )

        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        binding.getStartedButton.setOnClickListener {
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(R.id.action_onboardingFragment_to_categoriesFragment)
        }

        return binding.root
    }

}