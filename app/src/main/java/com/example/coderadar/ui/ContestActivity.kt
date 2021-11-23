package com.example.coderadar.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.coderadar.presentation.viewmodel.ContestsViewModel
import com.example.coderadar.presentation.viewmodel.ContestsViewModelFactory
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.ActivityContestBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContestActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ContestsViewModelFactory
    @Inject
    lateinit var viewModel: ContestsViewModel
    private lateinit var binding:ActivityContestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = resources.getColor(R.color.buttonbackcolordark)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvContests.setupWithNavController(
            navController
        )

        viewModel =ViewModelProvider(this,factory).get(ContestsViewModel::class.java)
    }
}