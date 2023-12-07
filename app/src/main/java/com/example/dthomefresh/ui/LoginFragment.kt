package com.example.dthomefresh.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
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
import com.example.dthomefresh.utils.KeyboardUtils
import com.example.dthomefresh.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout

    lateinit var googleSignInClient: GoogleSignInClient
    private val REQ_ONE_TAP = 2
    private val TAG = "LoginFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.lifecycleOwner = this

        val animationView: LottieAnimationView = binding.animLogin

        editTextEmail = binding.etUsername
        editTextPassword = binding.etPassword
        textInputLayoutEmail = binding.tiUsername
        textInputLayoutPassword = binding.tiPassword

        var email: String
        var password: String

        binding.btLogin.setOnClickListener {
            KeyboardUtils.hideKeyboard(requireActivity())
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()

            if(isEmpty(email) && isEmpty(password)) {
                textInputLayoutEmail.error = "Email cannot be empty"
                textInputLayoutPassword.error = "Password cannot be empty"
            }
            else if(isEmpty(email)) {
                textInputLayoutPassword.error = null
                textInputLayoutEmail.error = "Email cannot be empty"
            }
            else if(isEmpty(password)) {
                textInputLayoutEmail.error = null
                textInputLayoutPassword.error = "Password cannot be empty"
            }
            else if(!isValidEmail(email)) {
                textInputLayoutEmail.error = "Invalid email address"
            }
            else{
                textInputLayoutEmail.error = null
                textInputLayoutPassword.error = null
                viewModel.setEmail(email)
                viewModel.setPassword(password)
                viewModel.startSignIn()
                viewModel.startLoginAnimation()
            }
        }

        viewModel.signInSuccess.observe(viewLifecycleOwner, Observer { newSignInSuccess ->
            if(newSignInSuccess == true) {
                Toast.makeText(requireContext(),"Login Successful", Toast.LENGTH_SHORT).show()
                viewModel.stopLoginAnimation()
                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_categoriesFragment)
            }
            else {
                Toast.makeText(requireContext(),"Login Failed, please check your credentials", Toast.LENGTH_SHORT).show()
                viewModel.stopLoginAnimation()
            }
        })

        viewModel.loginAnimation.observe(viewLifecycleOwner, Observer { shouldAnimate ->
            if (shouldAnimate) {
                animationView.playAnimation()
                animationView.visibility = View.VISIBLE
            } else {
                animationView.cancelAnimation()
                animationView.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        })

        binding.btSignUp.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btGoogleSignIn.setOnClickListener {
            viewModel.startLoginAnimation()
            signInWithGoogle()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_ONE_TAP) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken

                if (idToken != null) {
                    // Sign in to Firebase with Google credentials
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                // Firebase authentication successful, user is signed in
                                val firebaseUser = FirebaseAuth.getInstance().currentUser
                                Snackbar.make(binding.root, "Login Successful", Snackbar.LENGTH_SHORT).show()
                                Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_categoriesFragment)
                                viewModel.stopLoginAnimation()
                            } else {
                                // Firebase authentication failed, handle the error
                                Log.w(TAG, "signInWithCredential:failure", signInTask.exception)
                                viewModel.stopLoginAnimation()
                            }
                        }
                } else {
                    Log.e(TAG, "ID token is null")
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign-in failed: ${e.statusCode}")
            }
        }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, REQ_ONE_TAP)
        }
    }

    private fun isEmpty(string: String): Boolean {
        return TextUtils.isEmpty(string)
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}