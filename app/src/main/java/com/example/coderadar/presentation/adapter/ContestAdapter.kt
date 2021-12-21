package com.example.coderadar.presentation.adapter

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.ContestListItemBinding
import com.example.coderadar.data.model.Contest
import com.example.coderadar.mvvm.LoginViewModel
import com.example.coderadar.reminderReceiver.AlarmReceiver
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class ContestAdapter(private val context: Context, private val loginViewModel: LoginViewModel, private val owner: LifecycleOwner): RecyclerView.Adapter<ContestAdapter.contestViewHolder>() {
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

                when (contest.resource) {
                    "codeforces.com" -> binding.sourceIcon.setImageResource(R.drawable.ic_code_forces)
                    "leetcode.com"->binding.sourceIcon.setImageResource(R.drawable.ic_leetcode)
                    "hackerrank.com"->binding.sourceIcon.setImageResource(R.drawable.ic_hackerrank)
                    "codingcompetitions.withgoogle.com"->binding.sourceIcon.setImageResource(R.drawable.google_logo)
                    "codechef.com"-> binding.sourceIcon.setImageResource(R.drawable.ic_codechef)
                    "hackerearth.com"->binding.sourceIcon.setImageResource(R.drawable.ic_hackerearth)
                }



//                binding.sourceIcon.setImageResource(R.id.)

                binding.linkBtn.setOnClickListener {
                    val url = contest.href
                    val intent = Intent()
                    intent.setPackage("com.android.chrome")
                    intent.action = Intent.ACTION_VIEW
                    intent.setData(Uri.parse(url))
                    context.startActivity(intent)
                }

                binding.reminderBtn.setOnClickListener {
                    if (currentDate.isBefore(startDate)){
                        val time : LocalDateTime = LocalDateTime.parse(contest.start)
                        val calendar = Calendar.getInstance()
                        calendar.clear()
                        calendar.set(time.year, time.monthValue - 1, time.dayOfMonth, time.hour, time.minute, time.second)
                        val remainingTime = calendar.timeInMillis

                        loginViewModel.addContestintofirebase(contest)
                        loginViewModel.contestSavedStatus.observe(owner, {
                            if (it == true) {
                                Toast.makeText(context, "Alarm is set successfully", Toast.LENGTH_LONG).show()

//                                val intent = Intent(context, AlarmReceiver::class.java)
//                                intent.putExtra("notificationtitle", contest.event)
//                                intent.putExtra("Url", contest.href)
//                                val pendingIntent = PendingIntent.getBroadcast(
//                                    context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
//                                )
//
//
//                                val alarmManager = context.getSystemService(Service.ALARM_SERVICE) as AlarmManager
//                                alarmManager.setExact(
//                                    AlarmManager.RTC_WAKEUP, remainingTime + 5 * 60 * 1000,
//                                    pendingIntent)
                            } else {
                                Toast.makeText(context, "An error occurred please try again later.", Toast.LENGTH_LONG).show()
                            }
                        })



                    } else {
                    Toast.makeText(context, "This contest is Live now, You can not set reminder.", Toast.LENGTH_LONG).show()
                    }

                }


            }


    }

}