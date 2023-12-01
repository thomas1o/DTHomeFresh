package com.example.dthomefresh.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.data.Seller
import com.example.dthomefresh.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("Sellers")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentProfileBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile, container, false
        )

        var name: String
        var phoneNumber: String
        var address: String

        auth = Firebase.auth

        binding.saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            name = binding.etName.text.toString()
            phoneNumber = binding.etPhoneNumber.text.toString()
            address = binding.etAddress.text.toString()

            writeNewUser(name, phoneNumber, address)

        }

        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        val userEmail = currentUser?.email
        binding.userEmail.text = userEmail.toString()

//        binding.btImageUpload.setOnClickListener {
//            Toast.makeText(requireContext(), "Upload button pressed", Toast.LENGTH_SHORT).show()
//        }

        binding.signOutButton.setOnClickListener{
            Firebase.auth.signOut()
            Toast.makeText(requireContext(), "Sign out successful", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_categoriesFragment)
        }

        return binding.root
    }


    private fun writeNewUser(name: String, phoneNumber: String, address: String) {
        val seller = Seller(name, phoneNumber, address)
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        currentUser?.let { firebaseUser ->
            val userEmail = firebaseUser.email // Retrieve the user's email

            // Note: Check if the user's email is not null
            userEmail?.let { email ->
                // Note: Encode the email to replace characters like "." and "#" which are invalid in Firebase keys
                val encodedEmail = email.replace(".", "_").replace("#", "_")

                // Note: Write user data to the Firebase Realtime Database with the email as the key
                database.reference.child("Sellers").child(encodedEmail).setValue(seller)
            }
        }
    }

}