package com.example.dthomefresh.sellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentCategoriesBinding
import com.example.dthomefresh.databinding.FragmentSellersBinding

class SellersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentSellersBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sellers, container, false
        )

        return binding.root
    }

}