package com.example.dthomefresh.categories

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private var optionSelected: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCategoriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_categories, container, false
        )
        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        binding.cardView1.setOnClickListener {
            assignOptionValue(0)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(CategoriesFragmentDirections.actionCategoriesFragmentToSellersFragment(optionSelected))
        }

        binding.cardView2.setOnClickListener {
            assignOptionValue(1)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(CategoriesFragmentDirections.actionCategoriesFragmentToSellersFragment(optionSelected))
        }

        binding.cardView3.setOnClickListener {
            assignOptionValue(2)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(CategoriesFragmentDirections.actionCategoriesFragmentToSellersFragment(optionSelected))
        }

        binding.menuButton.setOnClickListener {
            Toast.makeText(context, "Menu Button Pressed", Toast.LENGTH_SHORT).show();
        }

        return binding.root
    }
    private fun assignOptionValue(number: Int) {
        optionSelected = number
    }
}