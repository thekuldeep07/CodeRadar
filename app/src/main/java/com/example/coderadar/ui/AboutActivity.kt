package com.example.coderadar.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.AboutusItemBinding
import com.example.CodeRadar.databinding.ActivityAboutBinding
import com.example.CodeRadar.databinding.ActivityContestBinding
import com.example.coderadar.data.model.Developer
import com.example.coderadar.presentation.adapter.AboutUsAdapter

class AboutActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAboutBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_about)

        val recyclerview = findViewById<RecyclerView>(R.id.aboutUs_rv)
        val contribute = findViewById<TextView>(R.id.contribute)
        recyclerview.layoutManager= LinearLayoutManager(this)

        val data = ArrayList<Developer>()

         val kuldeep : Developer = Developer(R.drawable.kuldeep,"Kuldeep Gangwar","Developer","Tester",
         "https://github.com/thekuldeep07/thekuldeep07","https://www.linkedin.com/in/thekuldeep07/",
             "CSE 2019-2023",
         R.drawable.ic_baseline_settings_24,R.drawable.tester)


        val roop : Developer = Developer(R.drawable.roop,"Roop Kumar","Developer","Tester",
            "https://github.com/RooP-Kumar","https://www.linkedin.com/in/roop-kumar-71a084193/",
            "CSE 2019-2023",R.drawable.ic_baseline_settings_24,R.drawable.tester)

        val hardik : Developer = Developer(R.drawable.hardik,"Hardik Parashari","Developer","UI Designer",
            "https://github.com/Hardi-k-24","https://www.linkedin.com/in/hardik-parashari",
            "CSE 2019-2023",R.drawable.ic_baseline_settings_24,R.drawable.ic_baseline_brush_24)

        val sharik : Developer = Developer(R.drawable.sharik,"Sharik Khan","Developer","UI Designer",
            "https://github.com/pashasharik","https://www.linkedin.com/mwlite/in/sharik-khan-7a8985197",
            "CSE 2019-2023",R.drawable.ic_baseline_settings_24,R.drawable.ic_baseline_brush_24)

        data.addAll(listOf(kuldeep,roop,hardik,sharik))

        // This will pass the ArrayList to our Adapter
        val adapter = AboutUsAdapter(this,data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        contribute.setOnClickListener{
            val url = "https://github.com/thekuldeep07/CodeRadar"
            val githubUri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, githubUri)
            this.startActivity(intent)

        }


    }
}