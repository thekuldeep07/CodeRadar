package com.example.coderadar.OnboardFragments

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coderadar.MainActivity
import com.example.CodeRadar.R

class OnboardAdapter(private val NUM_PAGES: Int, private val con: Activity): RecyclerView.Adapter<OnboardAdapter.myHolder>() {
    class myHolder(view: View): RecyclerView.ViewHolder(view) {
        val onboardText = view.findViewById<TextView>(R.id.onBoardText)
        val secondOnboardText = view.findViewById<TextView>(R.id.secondonboardtext)
        val onboardImage = view.findViewById<ImageView>(R.id.onboardimage)
        val onboardPaging = view.findViewById<ImageView>(R.id.onboardpaging)
        val firstonboardshowwelcomelogo = view.findViewById<LinearLayout>(R.id.firstonboardshowwelcomelogo)
        val firstonboardtext = view.resources.getString(R.string.firstonboardtext)
        val secondonboardtext = view.resources.getString(R.string.secondonboardtext)
        val thirdonboardtext = view.resources.getString(R.string.thirdonboardtext)
        val button = view.findViewById<Button>(R.id.onBoardButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myHolder {
        val viewi = LayoutInflater.from(parent.context).inflate(R.layout.onboard_screen_layout, parent, false)
        return myHolder(viewi)
    }

    override fun onBindViewHolder(holder: myHolder, position: Int) {
        if (position == 0) {
            holder.onboardText.text = holder.firstonboardtext
        } else if (position == 1) {
            holder.firstonboardshowwelcomelogo.visibility = View.GONE
            holder.onboardText.text = holder.secondonboardtext
            holder.onboardPaging.setImageResource(R.drawable.secondposition)
            holder.onboardImage.setImageResource(R.drawable.secondonboard)
            holder.secondOnboardText.visibility = View.VISIBLE
        } else if (position == 2) {
            holder.firstonboardshowwelcomelogo.visibility = View.GONE
            holder.onboardText.text = holder.thirdonboardtext
            holder.onboardPaging.setImageResource(R.drawable.thirdposition)
            holder.onboardImage.setImageResource(R.drawable.thirdonboard)
            holder.button.visibility = View.VISIBLE
            holder.button.setOnClickListener {
                con.startActivity(Intent(con, MainActivity::class.java))
                con.finish()
            }
        }
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

}