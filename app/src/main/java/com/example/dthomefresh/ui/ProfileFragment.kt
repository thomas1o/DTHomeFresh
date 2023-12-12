package com.example.dthomefresh.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentProfileBinding
import com.example.dthomefresh.viewmodel.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val signOutAnimation = binding.animSignOut

        var name: String
        var phoneNumber: String
        var address: String

        binding.saveButton.setOnClickListener {
            Snackbar.make(binding.root, "Saved successfully", Snackbar.LENGTH_SHORT).show()

            name = binding.etName.text.toString()
            phoneNumber = binding.etPhoneNumber.text.toString()
            address = binding.etAddress.text.toString()

            viewModel.writeNewUser(name, phoneNumber, address)

            // Note: Clear EditText fields after saving
            binding.etName.text?.clear()
            binding.etPhoneNumber.text?.clear()
            binding.etAddress.text?.clear()

        }

        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email
        binding.userEmail.text = userEmail.toString()

        binding.signOutButton.setOnClickListener {
            signOutAnimation.playAnimation()
            signOutAnimation.visibility = View.VISIBLE
            Firebase.auth.signOut()
            Handler(Looper.getMainLooper()).postDelayed({
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_profileFragment_to_categoriesFragment)
                signOutAnimation.cancelAnimation()
                Snackbar.make(binding.root, "Signed out successfully", Snackbar.LENGTH_SHORT).show()
            }, 1000)
        }

        return binding.root
    }

}