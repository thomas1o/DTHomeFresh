package com.example.dthomefresh.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.dthomefresh.utils.Validator
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
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val animationView: LottieAnimationView = binding.animLogin

        editTextEmail = binding.etUsername
        editTextPassword = binding.etPassword
        textInputLayoutEmail = binding.tiUsername
        textInputLayoutPassword = binding.tiPassword

        var email: String
        var password: String
        val validator = Validator()

        binding.btLogin.setOnClickListener {
            KeyboardUtils.hideKeyboard(requireActivity())
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()

            textInputLayoutEmail.error = validator.emailValidator(email)
            textInputLayoutPassword.error = validator.passwordValidator(password)

            if (validator.emailValidator(email) == null && validator.passwordValidator(password) == null) {
                viewModel.setEmail(email)
                viewModel.setPassword(password)
                viewModel.startSignIn()
                viewModel.startLoginAnimation()
            }
        }

        viewModel.signInSuccess.observe(viewLifecycleOwner, Observer { newSignInSuccess ->
            if (newSignInSuccess == true) {
                Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                viewModel.stopLoginAnimation()
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_loginFragment_to_categoriesFragment)
            } else {
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
                Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).show()
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
                                val firebaseUser = FirebaseAuth.getInstance().currentUser
                                Snackbar.make(
                                    binding.root,
                                    "Logged in with ${firebaseUser?.email}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                Navigation.findNavController(requireView())
                                    .navigate(R.id.action_loginFragment_to_categoriesFragment)
                                viewModel.stopLoginAnimation()
                            } else {
                                val e = signInTask.exception
                                Log.w(TAG, "signInWithCredential:failure", e)
                                viewModel.displayError(e)
                                viewModel.stopLoginAnimation()
                            }
                        }
                } else {
                    Log.e(TAG, "ID token is null")
                    Snackbar.make(
                        binding.root,
                        "Something went wrong. Please try again later.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Log.w(TAG, "Google sign-in failed: $e")
                viewModel.displayError(e)
                viewModel.stopLoginAnimation()
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

//    private fun isEmpty(string: String): Boolean {
//        return TextUtils.isEmpty(string)
//    }
//
//    private fun isValidEmail(email: String): Boolean {
//        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }

}