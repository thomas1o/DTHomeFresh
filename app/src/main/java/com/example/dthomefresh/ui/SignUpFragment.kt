package com.example.dthomefresh.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.dthomefresh.R
import com.example.dthomefresh.databinding.FragmentSignUpBinding
import com.example.dthomefresh.viewmodel.SignUpViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var viewModel: SignUpViewModel
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var editTextRePassword: TextInputEditText
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var textInputLayoutRePassword: TextInputLayout

    private lateinit var googleSignInClient: GoogleSignInClient
    private val REQ_ONE_TAP = 2
    private val TAG = "SignUpFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up, container, false
        )

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val animationView: LottieAnimationView = binding.animSignUp

        editTextEmail = binding.etUsername
        editTextPassword = binding.etPassword
        editTextRePassword = binding.etRePassword
        textInputLayoutEmail = binding.tiUsername
        textInputLayoutPassword = binding.tiPassword
        textInputLayoutRePassword = binding.tiRePassword

        var email: String
        var password: String
        var rePassword: String

        //NOTE: For styling the Skip for now button
//        val skipForNowButton = binding.btSkipForNow
//        val content = SpannableString("Skip for now")
//        content.setSpan(UnderlineSpan(), 0, content.length, 0)
//        skipForNowButton.text = content

        binding.btSignUp.setOnClickListener {
            email = editTextEmail.text.toString()
            password = editTextPassword.text.toString()
            rePassword = editTextRePassword.text.toString()

            if (isEmpty(email) && isEmpty(password)) {
                textInputLayoutEmail.error = "Email cannot be empty"
                textInputLayoutPassword.error = "Password cannot be empty"
            } else if (isEmpty(email)) {
                textInputLayoutPassword.error = null
                textInputLayoutEmail.error = "Email cannot be empty"
            } else if (!isValidPassword(password)) {
                textInputLayoutEmail.error = null
                textInputLayoutPassword.error = "Cannot be less than 6 character"
            } else if (!isValidEmail(email)) {
                textInputLayoutEmail.error = "Invalid email address"
            } else if (password != rePassword) {
                textInputLayoutEmail.error = null
                textInputLayoutPassword.error = null
                textInputLayoutRePassword.error = "Passwords do not match"
            } else {
                textInputLayoutEmail.error = null
                textInputLayoutPassword.error = null
                textInputLayoutRePassword.error = null
                viewModel.setEmail(email)
                viewModel.setPassword(password)
                viewModel.startSignUp()
                viewModel.startLoginAnimation()
            }
        }

        binding.btGoogleSignIn.setOnClickListener {
            viewModel.startLoginAnimation()
            signInWithGoogle()
        }

        binding.loginButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.signUpSuccess.observe(viewLifecycleOwner, Observer { newSignUpSuccess ->
            if (newSignUpSuccess == true) {
                Snackbar.make(
                    binding.root,
                    "Sign up successful, please continue to login",
                    Snackbar.LENGTH_SHORT
                ).show()
                viewModel.stopLoginAnimation()
                findNavController().popBackStack()
            } else {
                viewModel.stopLoginAnimation()
            }
        })

        viewModel.signUpAnimation.observe(viewLifecycleOwner, Observer { shouldAnimate ->
            if (shouldAnimate) {
                animationView.setAnimation(R.raw.anim_yellow_processing_circle)
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
                    val credential = GoogleAuthProvider.getCredential(idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                // Firebase authentication successful, user is signed in
                                val firebaseUser = FirebaseAuth.getInstance().currentUser
                                Snackbar.make(
                                    binding.root,
                                    "Logged in with ${firebaseUser?.email}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                Navigation.findNavController(requireView())
                                    .navigate(R.id.action_signUpFragment_to_categoriesFragment)
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
                    Snackbar.make(binding.root, "Something went wrong. Please try again later.", Snackbar.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign-in failed: ${e.statusCode}")
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

    private fun isEmpty(string: String): Boolean {
        return TextUtils.isEmpty(string)
    }

    private fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}