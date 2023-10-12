package com.example.dthomefresh.sellers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        val binding: FragmentSellersBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sellers, container, false
        )

        viewModel = ViewModelProvider(this).get(SellersViewModel::class.java)
        binding.sellersViewModel = viewModel

        binding.upButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_categoriesFragment)
        }
        //TODO what does the buttons do
        binding.cardNavigation1.setOnClickListener{

        }

        binding.cardNavigation2.setOnClickListener{
            binding.cardNavigation1.cardBackgroundColor
        }

        binding.cardNavigation3.setOnClickListener{

        }

        return binding.root
    }

}