package com.example.dthomefresh.signup

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentLoginBinding
import com.example.dthomefresh.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText

//    TODO find the use of onCreate in Fragment, difference when it it initialised in both
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//            auth = Firebase.auth
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up, container, false
        )

        auth = Firebase.auth

        editTextEmail = binding.etUsername
        editTextPassword = binding.etPassword
        var email: String
        var password: String

        binding.btSignUp.setOnClickListener {
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()

            if(isEmpty(email) && isEmpty(password)) {
                Toast.makeText(requireContext(), "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(isEmpty(email)) {
                Toast.makeText(requireContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(isEmpty(password)) {
                Toast.makeText(requireContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(),"Sign Up successful. Please continue to login",Toast.LENGTH_SHORT).show()
                            Firebase.auth.signOut()
                            Navigation.findNavController(it).navigate(R.id.action_signUpFragment_to_loginFragment)
                        } else {
                            Toast.makeText(requireContext(),"Account creation failed",Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.loginButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        return binding.root
    }

    private fun isEmpty(string: String): Boolean {
        return TextUtils.isEmpty(string)
    }

}