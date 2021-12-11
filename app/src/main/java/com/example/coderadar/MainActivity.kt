package com.example.coderadar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        window.statusBarColor = resources.getColor(R.color.black)
        setContentView(binding.root)

    }
}
