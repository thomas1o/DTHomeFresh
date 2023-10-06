package com.example.dthomefresh.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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

        binding.getStartedButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_onboardingFragment_to_categoriesFragment)
        )

        return binding.root
    }

}