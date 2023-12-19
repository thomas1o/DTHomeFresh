package com.example.dthomefresh.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentAddressBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddressBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddressBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address_bottom_sheet, container, false)

        return binding.root
    }

}