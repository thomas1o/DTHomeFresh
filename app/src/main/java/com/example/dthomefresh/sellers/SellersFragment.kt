package com.example.dthomefresh.sellers

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentCategoriesBinding
import com.example.dthomefresh.databinding.FragmentSellersBinding

class SellersFragment : Fragment() {

    private lateinit var viewModel: SellersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val option = mutableMapOf(
            "food" to false,
            "clothes" to false,
            "homemade_items" to false
        )

        val binding: FragmentSellersBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sellers, container, false
        )

        viewModel = ViewModelProvider(this).get(SellersViewModel::class.java)
        binding.sellerViewModel = viewModel

        binding.upButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_categoriesFragment)
        }
        //TODO- figure out a better logic
        binding.cardNavigation1.setOnClickListener {
            option["food"] = true
            if(option["food"] == true) {
                binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_seller_navigation))
                binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_seller_navigation))
                binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
                binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
                binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
                binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
            }
        }
        binding.cardNavigation2.setOnClickListener {
            option["clothes"] = true
            if(option["clothes"] == true) {
                binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
                binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
                binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_seller_navigation))
                binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_seller_navigation))
                binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
                binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
            }
        }
        binding.cardNavigation3.setOnClickListener {
            option["homemade_items"] = true
            if(option["homemade_items"] == true) {
                binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
                binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
                binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
                binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
                binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_seller_navigation))
                binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_seller_navigation))
            }
        }
        return binding.root
    }
}