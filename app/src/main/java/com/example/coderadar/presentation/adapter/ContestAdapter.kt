package com.example.coderadar.presentation.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.CodeRadar.databinding.ContestListItemBinding
import com.example.coderadar.data.model.Contest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ContestAdapter: RecyclerView.Adapter<ContestAdapter.contestViewHolder>() {


    private val callback = object : DiffUtil.ItemCallback<Contest>() {
        override fun areItemsTheSame(oldItem: Contest, newItem: Contest): Boolean {
            return  oldItem.event == newItem.event
        }

        override fun areContentsTheSame(oldItem: Contest, newItem: Contest): Boolean {
            return  oldItem == newItem
        }

    }


    val  differ = AsyncListDiffer(this, callback)
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contestViewHolder {
        val  binding = ContestListItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return  contestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: contestViewHolder, position: Int) {
        val contest=differ.currentList[position]
        Log.d("kul", "" + contest.href)
        holder.bind(contest)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class  contestViewHolder(val binding: ContestListItemBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(contest: Contest){
                binding.eventTitle.text=contest.event


                val startDate = LocalDateTime.parse(contest.start)
                val endDate = LocalDateTime.parse(contest.end)
                val startTime = (startDate.hour.toString() + ":" + startDate.minute.toString())
                val endTime = (endDate.hour.toString() + ":" + endDate.minute.toString())
                binding.startDate.text=(startDate.dayOfMonth.toString() +
                        "-"+startDate.month.toString()+"-"+startDate.year.toString())
                binding.endDate.text=(endDate.dayOfMonth.toString() +
                        "-"+endDate.month.toString()+"-"+endDate.year.toString())

                binding.startTime.text = startTime.toString()
                binding.endTime.text = endTime.toString()

                val currentDate = LocalDateTime.now()
                if (startDate.isAfter(currentDate)){
                    binding.statusTv.text = "Upcoming"
                    binding.statusTv.setTextColor(Color.RED)
                }
                else{
                    if (currentDate.isBefore(endDate)){
                        binding.statusTv.text = "Live"
                        binding.statusTv.setTextColor(Color.GREEN)
                    }


                }
            }
    }

}