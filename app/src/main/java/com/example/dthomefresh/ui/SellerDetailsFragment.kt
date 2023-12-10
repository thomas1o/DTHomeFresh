package com.example.dthomefresh.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentSellerDetailsBinding
import com.example.dthomefresh.utils.openDialPad
import com.example.dthomefresh.utils.openWhatsapp

class SellerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSellerDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_seller_details, container, false
        )

        binding.btCall.setOnClickListener {
            openDialPad(requireContext(), "919961539011")
        }

        binding.btWhatsapp.setOnClickListener {
            openWhatsapp(requireContext(), "919961539011")
        }

        return binding.root
    }

}