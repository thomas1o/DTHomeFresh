package com.example.dthomefresh.sellers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentSellersBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SellersFragment : Fragment() {

    private lateinit var binding: FragmentSellersBinding
    private lateinit var viewModel: SellersViewModel
    private lateinit var viewModelFactory: SellersViewModelFactory
    private lateinit var auth: FirebaseAuth
    private var loggedIn: Boolean = false

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            loggedIn = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sellers, container, false
        )

        viewModelFactory = SellersViewModelFactory(SellersFragmentArgs.fromBundle(requireArguments()).optionSelected)
        viewModel = ViewModelProvider(this, viewModelFactory)[SellersViewModel::class.java]
        binding.sellerViewModel = viewModel

        auth = Firebase.auth

        binding.upButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_categoriesFragment)
        }

        binding.profileButton.setOnClickListener{
            if(!loggedIn)
                Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_loginFragment)
            else
                Toast.makeText(requireContext(), "You are already logged in", Toast.LENGTH_SHORT).show()
        }

        viewModel.options.observe(viewLifecycleOwner, Observer { newOptions ->
            if(newOptions[0] == true) {
                setDefaultColor()
                binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_seller_navigation))
                binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_seller_navigation))
            }
            else if(newOptions[1] == true) {
                setDefaultColor()
                binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_seller_navigation))
                binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_seller_navigation))
            }
            else if(newOptions[2] == true) {
                setDefaultColor()
                binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_seller_navigation))
                binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_seller_navigation))
            }
        })

        return binding.root
    }
    private fun setDefaultColor() {
        binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
        binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
        binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
        binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
        binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_dark))
        binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_lighter))
    }
}