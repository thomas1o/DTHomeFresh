package com.example.dthomefresh.signup

import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
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
    private lateinit var editTextRePassword: TextInputEditText

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
        editTextRePassword = binding.etRePassword

        var email: String
        var password: String
        var rePassword: String

        //For styling the Skip for now button
        val skipForNowButton = binding.btSkipForNow
        val content = SpannableString("Skip for now")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        skipForNowButton.text = content

        binding.btSignUp.setOnClickListener {
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()
            rePassword = editTextRePassword.text.toString()


            if(isEmpty(email) && isEmpty(password)) {
                Toast.makeText(requireContext(), "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(isEmpty(email)) {
                Toast.makeText(requireContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(isEmpty(password)) {
                Toast.makeText(requireContext(), "Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if(password != rePassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
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

        binding.btSkipForNow.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_signUpFragment_to_categoriesFragment)
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