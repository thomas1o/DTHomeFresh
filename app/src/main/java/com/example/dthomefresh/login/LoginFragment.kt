package com.example.dthomefresh.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputEditText

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val animationView: LottieAnimationView = binding.lottieAnimationView

        editTextEmail = binding.etUsername
        editTextPassword = binding.etPassword
        var email: String
        var password: String

        binding.lifecycleOwner = this

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
                viewModel.setEmail(email)
                viewModel.setPassword(password)
                viewModel.startSignIn()
                viewModel.startLoginAnimation()
            }
        }

        viewModel.signInSuccess.observe(viewLifecycleOwner, Observer { newSignInSuccess ->
            if(newSignInSuccess == true) {
                Toast.makeText(requireContext(),"Login Successful", Toast.LENGTH_SHORT,).show()
                viewModel.stopLoginAnimation()
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_categoriesFragment)
            }
        })

        viewModel.loginAnimation.observe(viewLifecycleOwner, Observer { shouldAnimate ->
            if (shouldAnimate) {
                animationView.setAnimation(R.raw.processing_circle)
                animationView.playAnimation()
                animationView.visibility = View.VISIBLE
            } else {
                animationView.cancelAnimation()
                animationView.visibility = View.GONE
            }
        })

        binding.btSignUp.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        return binding.root
    }

    private fun isEmpty(string: String): Boolean {
        return TextUtils.isEmpty(string)
    }

}