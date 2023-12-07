package com.example.dthomefresh.ui

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dthomefresh.MainActivity
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentCategoriesBinding
import com.example.dthomefresh.utils.FragmentHandler
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class CategoriesFragment : Fragment() {

    private var optionSelected: Int = -1
    private lateinit var auth: FirebaseAuth
    private var loggedIn: Boolean = false

    override fun onStart() {
        super.onStart()
        loggedInCheck()
        if(!loggedIn) {
//            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCategoriesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_categories, container, false
        )

        val fragmentHandler = FragmentHandler(requireActivity() as MainActivity)
        binding.fragmentHandler = fragmentHandler

        auth = Firebase.auth

        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        binding.cardView1.setOnClickListener {
            assignOptionValue(0)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(CategoriesFragmentDirections.actionCategoriesFragmentToSellerListFragment(optionSelected))
        }

        binding.cardView2.setOnClickListener {
            assignOptionValue(1)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(CategoriesFragmentDirections.actionCategoriesFragmentToSellerListFragment(optionSelected))
        }

        binding.cardView3.setOnClickListener {
            assignOptionValue(2)
            vibrator.vibrate(100)
            Navigation.findNavController(it).navigate(CategoriesFragmentDirections.actionCategoriesFragmentToSellerListFragment(optionSelected))
        }


        binding.profileButton.setOnClickListener {
            if(loggedIn)
                Navigation.findNavController(requireView()).navigate(R.id.action_categoriesFragment_to_profileFragment)
            else
                Navigation.findNavController(requireView()).navigate(R.id.action_categoriesFragment_to_loginFragment)
        }

        return binding.root
    }
    private fun assignOptionValue(number: Int) {
        optionSelected = number
    }

    private fun loggedInCheck() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loggedIn = true
        }
    }
}

