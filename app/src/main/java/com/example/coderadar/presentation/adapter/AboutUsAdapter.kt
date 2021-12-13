package com.example.coderadar.presentation.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.CodeRadar.databinding.AboutusItemBinding
import com.example.coderadar.data.model.Developer
import java.util.*


class AboutUsAdapter(private val context: Context, private val developerList: List<Developer>): RecyclerView.Adapter<AboutUsAdapter.aboutUsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): aboutUsViewHolder {
        val  binding = AboutusItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return  aboutUsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: aboutUsViewHolder, position: Int) {
        holder.bind(developerList[position])
    }

    override fun getItemCount(): Int {
        return developerList.size
    }

    inner class  aboutUsViewHolder(val binding: AboutusItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(developer: Developer){
            binding.memberName.text=developer.name
            binding.memberImage.setImageResource(developer.image)
            binding.role1.text=developer.job1
            binding.role2.text=developer.job2
            binding.batch.text=developer.batch
            binding.role1image.setImageResource(developer.job1Icon)
            binding.role2image.setImageResource(developer.job2Icon)

            binding.linkedInImage.setOnClickListener {
                    val url = developer.linkedin
                    val linkedUri =
                        Uri.parse(url)
                    val intent = Intent(Intent.ACTION_VIEW, linkedUri)
                    context.startActivity(intent)
            }


            binding.githubImage.setOnClickListener {
                val url = developer.github
                val githubUri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW, githubUri)
                context.startActivity(intent)
                }


            }


        }

    }

