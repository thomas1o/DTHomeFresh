package com.example.dthomefresh.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentSellerDetailsBinding
import com.example.dthomefresh.databinding.FragmentSellerListBinding

class SellerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSellerDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_seller_details, container, false
        )

        return binding.root
    }

}