package com.example.dthomefresh.signup

import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.UnderlineSpan
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
import com.example.dthomefresh.databinding.FragmentSignUpBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var viewModel: SignUpViewModel
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextRePassword: TextInputEditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentSignUpBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up, container, false
        )

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        val animationView: LottieAnimationView = binding.lottieAnimationView

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
                Toast.makeText(requireContext(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
            }
            else if(password != rePassword) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
            else {
                viewModel.setEmail(email)
                viewModel.setPassword(password)
                viewModel.startSignUp()
                viewModel.startLoginAnimation()
            }
        }

        viewModel.signUpSuccess.observe(viewLifecycleOwner, Observer { newSignUpSuccess ->
            if(newSignUpSuccess == true) {
                Toast.makeText(requireContext(),"Sign up successful, please continue to login", Toast.LENGTH_SHORT,).show()
                viewModel.stopLoginAnimation()
                Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_loginFragment)
            }
        })

        viewModel.signUpAnimation.observe(viewLifecycleOwner, Observer { shouldAnimate ->
            if (shouldAnimate) {
                animationView.setAnimation(R.raw.processing_circle)
                animationView.playAnimation()
                animationView.visibility = View.VISIBLE
            } else {
                animationView.cancelAnimation()
                animationView.visibility = View.GONE
            }
        })

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