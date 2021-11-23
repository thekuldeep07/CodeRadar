package com.example.coderadar.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentLoginBinding
import com.example.coderadar.mvvm.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )
            .get(LoginViewModel::class.java)
        mainFunction()
        return binding.root
    }

    fun mainFunction() {
        // Setting up Login button
        binding.Login.setOnClickListener {
            val emailPattern: Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
            binding.progressBar.visibility = View.VISIBLE
            binding.mainConstrainLayout.alpha = 0.6f
            val email = binding.editText.text.toString()
            val pass = binding.editText2.text.toString()
            if (email.matches(emailPattern)) {
                viewModel.signIn(email, pass)
                viewModel.firebaseUserData.observe(this.requireActivity(), {
                    if (it != null) {
                        binding.progressBar.visibility = View.GONE
                        binding.mainConstrainLayout.alpha = 1f
                        binding.editText.setText("")
                        binding.editText2.setText("")
                        if (it.isEmailVerified) {
                            activity?.startActivity(Intent(activity, ContestActivity::class.java))
                            activity?.finish()
                            viewModel.removefirebaseUserData()
                        } else {
                            val snack = Snackbar.make(
                                requireActivity().findViewById(android.R.id.content),
                                "Please verify your email.",
                                Snackbar.LENGTH_LONG
                            )
                            val view = snack.view
                            val params = view.layoutParams as FrameLayout.LayoutParams
                            params.gravity = Gravity.TOP
                            snack.show()
                            viewModel.removefirebaseUserData()

                        }

                    }
                })
            } else {
                binding.progressBar.visibility = View.GONE
                binding.mainConstrainLayout.alpha = 1f
                binding.emailError.error = "Please enter a valid email."
            }

        }

        // Navigating to the Register Fragment
        binding.logintoregisterbutton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        // Launching Activity for Email id
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data = it.data
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        signInWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        Log.e("Api Exception", "${e}")
                    }
                }
            }

        // Setting onclick listener for signing with google account
        binding.loginWithGoogle.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            binding.mainConstrainLayout.alpha = 0.6f
            startingIntent()
        }

    }

    fun startingIntent() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("911082203780-11mcu4t8i6v85r6vdfa8h4shvgci8tcb.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val intent = googleSignInClient.signInIntent
        resultLauncher.launch(intent)
    }

    fun signInWithGoogle(idToken: String) {
        viewModel.loginWithGoogleAccount(idToken)
        Log.d("TAG", "you click on google sign in")
        viewModel.firebaseUserData.observe(this.requireActivity(), {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                binding.mainConstrainLayout.alpha = 1f
                binding.editText.setText("")
                binding.editText2.setText("")
                activity?.startActivity(Intent(activity, ContestActivity::class.java))
                activity?.finish()
            }
        })
    }
}