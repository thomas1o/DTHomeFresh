package com.example.dthomefresh.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dthomefresh.R
import com.example.dthomefresh.data.Seller
import com.example.dthomefresh.data.adapter.SellersListAdapter
import com.example.dthomefresh.databinding.FragmentSellerDetailsBinding

class SellerDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSellerDetailsBinding
    private lateinit var adapter: SellersListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_seller_details, container, false
        )

        binding.btUp.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_sellerDetailsFragment_to_sellerListFragment)
        }

//        binding.btCall.setOnClickListener {
//            openDialPad(requireContext(), "919961539011")
//        }
//
//        binding.btWhatsapp.setOnClickListener {
//            openWhatsapp(requireContext(), "919961539011")
//        }

        return binding.root
    }

}