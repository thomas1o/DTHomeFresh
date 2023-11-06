package com.example.dthomefresh.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentLoginBinding
import com.example.dthomefresh.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private val myRef = database.getReference("Sellers")

    private lateinit var name: String
    private lateinit var contact: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        auth = Firebase.auth

        binding.saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            name = binding.etName.toString()
            contact = binding.tiPhoneNumber.toString()


        }

        binding.signOutButton.setOnClickListener{
            Firebase.auth.signOut()
            Toast.makeText(requireContext(), "Sign out successful", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_sellersFragment)
        }

        return binding.root
    }

}