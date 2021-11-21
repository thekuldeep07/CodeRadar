package com.example.coderadar.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.coderadar.data.model.Contest
import com.example.CodeRadar.databinding.ContestListItemBinding

class ContestAdapter: RecyclerView.Adapter<ContestAdapter.contestViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<Contest>() {
        override fun areItemsTheSame(oldItem: Contest, newItem: Contest): Boolean {
            return  oldItem.event == newItem.event
        }

        override fun areContentsTheSame(oldItem: Contest, newItem: Contest): Boolean {
            return  oldItem == newItem
        }

    }


    val  differ = AsyncListDiffer(this,callback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contestViewHolder {
        val  binding = ContestListItemBinding.inflate(LayoutInflater.from(
                parent.context),parent,false)
        return  contestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: contestViewHolder, position: Int) {
        val contest=differ.currentList[position]
        Log.d("kul",""+contest.href)
        holder.bind(contest)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class  contestViewHolder(val binding : ContestListItemBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(contest: Contest){
                Log.d("mytag2",""+contest.href)
                binding.detatilTv.text=contest.event
                binding.siteName.text=contest.host


            }

    }

}