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
    private val contestList = ArrayList<Contest>()

    fun setList(contests:List<Contest>){
        contestList.clear()
        contestList.addAll(contests)
    }



    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contestViewHolder {
        val  binding = ContestListItemBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return  contestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: contestViewHolder, position: Int) {
        holder.bind(contestList[position])
    }

    override fun getItemCount(): Int {
        return contestList.size
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
//                if (( currentDate.compareTo(startDate) > 0 ) &&(currentDate.compareTo(endDate)<0)){
//                    binding.statusTv.text = "Live"
//                    binding.statusTv.setTextColor(Color.GREEN)
//
//                    //  0 comes when two date are same,
//                    //  1 comes when date1 is higher then date2
//                    // -1 comes when date1 is lower then date2
//
//                }
                if ((currentDate.isBefore(startDate))){
                    binding.statusTv.text = "upcoming"
                    binding.statusTv.setTextColor(Color.RED)
                }
                else{
                    if(endDate.isAfter(currentDate)){
                        binding.statusTv.text = "live"
                        binding.statusTv.setTextColor(Color.GREEN)
                    }


                }
            }
    }

}