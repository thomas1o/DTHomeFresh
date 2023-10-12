package com.example.dthomefresh.categories

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCategoriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_categories, container, false
        )

        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        binding.cardView1.setOnClickListener{
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(R.id.action_categoriesFragment_to_sellersFragment)
        }

        binding.cardView2.setOnClickListener{
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(R.id.action_categoriesFragment_to_sellersFragment)
        }

        binding.cardView3.setOnClickListener{
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(R.id.action_categoriesFragment_to_sellersFragment)
        }

        return binding.root
    }
}