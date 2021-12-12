package com.example.coderadar.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.coderadar.presentation.viewmodel.ContestsViewModel
import com.example.coderadar.presentation.viewmodel.ContestsViewModelFactory
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.ActivityContestBinding
import com.example.coderadar.MainActivity
import com.example.coderadar.mvvm.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import android.app.Notification
import android.app.NotificationManager
import androidx.core.app.NotificationCompat

import android.app.NotificationChannel

import android.annotation.SuppressLint
import android.graphics.Color

import android.os.Build





@AndroidEntryPoint
class ContestActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ContestsViewModelFactory
    @Inject
    lateinit var viewModel: ContestsViewModel
    lateinit var loginViewModel: LoginViewModel
    private lateinit var binding:ActivityContestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(LoginViewModel::class.java)
        window.statusBarColor = resources.getColor(R.color.buttonbackcolordark)
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvContests.setupWithNavController(
            navController
        )

        viewModel =ViewModelProvider(this,factory).get(ContestsViewModel::class.java)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.About){
//            startActivity(Intent(this, AboutActivity::class.java))
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val NOTIFICATION_CHANNEL_ID = "tutorialspoint_01"
            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setWhen(2000)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("sample notification")
                .setContentText("This is sample notification")
                .setContentInfo("Information")
            notificationManager.notify(1, notificationBuilder.build())

            return true
        } else if (item.itemId == R.id.logOut){
            loginViewModel.signOut()
            loginViewModel.userLogOutStatus.observe(this, {
                it?.let {
                    if (it) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            })
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
