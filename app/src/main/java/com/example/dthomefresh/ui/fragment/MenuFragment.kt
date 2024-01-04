package com.example.dthomefresh.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false
        )

        binding.btPartnerWithUs.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_menuFragment_to_contactUsFragment)
        }

        binding.btPartnerWithUs.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_menuFragment_to_mapsFragment)
        }

        return binding.root
    }

}