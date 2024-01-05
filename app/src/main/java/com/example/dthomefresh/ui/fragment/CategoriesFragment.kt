package com.example.dthomefresh.ui.fragment

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dthomefresh.ui.activity.MainActivity
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentCategoriesBinding
import com.example.dthomefresh.utils.FragmentHandler
import com.example.dthomefresh.utils.loggedInCheck

class CategoriesFragment : Fragment() {

    private var optionSelected: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCategoriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_categories, container, false
        )

        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        binding.cardView1.setOnClickListener {
            assignOptionValue(0)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(
                CategoriesFragmentDirections.actionCategoriesFragmentToSellerListFragment(
                    optionSelected
                )
            )
        }

        binding.cardView2.setOnClickListener {
            assignOptionValue(1)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(
                CategoriesFragmentDirections.actionCategoriesFragmentToSellerListFragment(
                    optionSelected
                )
            )
        }

        binding.cardView3.setOnClickListener {
            assignOptionValue(2)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(
                CategoriesFragmentDirections.actionCategoriesFragmentToSellerListFragment(
                    optionSelected
                )
            )
        }

        return binding.root
    }

    private fun assignOptionValue(number: Int) {
        optionSelected = number
    }

}

