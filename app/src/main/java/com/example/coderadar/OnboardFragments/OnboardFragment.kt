package com.example.coderadar.OnboardFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.CodeRadar.databinding.FragmentOnboardBinding

private const val NUM_PAGES = 3
class OnboardFragment : Fragment() {
    private lateinit var binding: FragmentOnboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardBinding.inflate(inflater, container, false)
        mainFunction()
        return binding.root
    }

    fun mainFunction() {
        Log.d("onboard", "onboard screen")
        val adapter = OnboardAdapter(NUM_PAGES, this.requireActivity())
        binding.onBoard.adapter = adapter
    }

}