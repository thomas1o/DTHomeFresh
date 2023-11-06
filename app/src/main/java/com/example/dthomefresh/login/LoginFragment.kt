package com.example.dthomefresh.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentLoginBinding
import com.example.dthomefresh.databinding.FragmentSellersBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var email: String
    private lateinit var password: String
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(requireContext(), "You are already logged in", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_sellersFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        auth = Firebase.auth

        editTextEmail = binding.etUsername
        editTextPassword = binding.password


        binding.btLogin.setOnClickListener {
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
            else{
                signIn(email, password)
            }
        }

        binding.signUpButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root
    }

    private fun isEmpty(string: String): Boolean {
        return TextUtils.isEmpty(string)
    }

    private fun signIn(email: String, password: String) {
        uiScope.launch {
            signInFromFirebase(email, password)
        }
    }

    private suspend fun signInFromFirebase(email: String, password: String) {
        withContext(Dispatchers.IO) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_SHORT,).show()
                        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_categoriesFragment)
                    }
                    else {
                        Toast.makeText(requireContext(),"Please enter the right credentials",Toast.LENGTH_SHORT,).show()
                    }
                }
        }
    }

}