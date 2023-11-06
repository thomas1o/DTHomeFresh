package com.example.dthomefresh.sellers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            if(loggedIn)
                Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_profileFragment)
            else
                Navigation.findNavController(it).navigate(R.id.action_sellersFragment_to_loginFragment)
        }

        viewModel.options.observe(viewLifecycleOwner, Observer { newOptions ->
            if(newOptions[0] == true) {
                setDefaultColor()
                binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_2))
                binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_2))
            }
            else if(newOptions[1] == true) {
                setDefaultColor()
                binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_2))
                binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_2))
            }
            else if(newOptions[2] == true) {
                setDefaultColor()
                binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow_2))
                binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_2))
            }
        })

        return binding.root
    }
    private fun setDefaultColor() {
        binding.navigationText1.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_1))
        binding.cardNavigation1.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_4))
        binding.navigationText2.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_1))
        binding.cardNavigation2.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_4))
        binding.navigationText3.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_1))
        binding.cardNavigation3.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_4))
    }
}