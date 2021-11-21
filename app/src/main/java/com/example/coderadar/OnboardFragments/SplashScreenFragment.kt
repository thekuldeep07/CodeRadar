package com.example.coderadar.OnboardFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentSplashScreenBinding
import com.example.coderadar.MainActivity
import com.example.coderadar.mvvm.LoginViewModel
import com.example.coderadar.ui.ContestActivity
import com.google.firebase.auth.FirebaseUser

class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var viewModel: LoginViewModel
    private var mUser: FirebaseUser? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )
            .get(LoginViewModel::class.java)
        mainFunction()
        return binding.root
    }

    private fun mainFunction() {
        val prefData = activity?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val openingTime = prefData?.getInt("openTime", 0)
        if (openingTime == 0) {
            Handler(Looper.getMainLooper()).postDelayed({
                Log.d("onboard", "splash onboard")
                findNavController().navigate(R.id.action_splashScreenFragment_to_onboardFragment)
                val editor = prefData.edit()
                editor.putInt("openTime", 1)
                editor.apply()
            }, 1000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.isAuthenticated()
                viewModel.isAuthenticated.observe(requireActivity(), {
                    it?.let {
                        if (it) {
                            activity?.startActivity(Intent(activity, ContestActivity::class.java))
                            activity?.finish()
                        } else {
                            activity?.startActivity(Intent(activity, MainActivity::class.java))
                            activity?.finish()
                        }
                    }

                })

            }, 1000)
        }

    }

}