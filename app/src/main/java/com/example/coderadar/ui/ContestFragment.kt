package com.example.coderadar.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coderadar.data.util.Resource
import com.example.coderadar.presentation.adapter.ContestAdapter
import com.example.coderadar.presentation.viewmodel.ContestsViewModel
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentContestBinding
import java.time.LocalDate
import java.time.LocalDateTime


class ContestFragment : Fragment() {
    private lateinit var viewmodel: ContestsViewModel
    private lateinit var  fragmentContestBinding: FragmentContestBinding
    private lateinit var contestAdapter: ContestAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contest, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentContestBinding = FragmentContestBinding.bind(view)
        viewmodel = (activity as ContestActivity).viewModel
        initRecyclerView()
        viewNewsList()
    }

    private fun viewNewsList() {
        val currentDateTimeMin = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
        val currentDateTime = LocalDateTime.now()
        Log.d("date",""+currentDateTimeMin)
        viewmodel.getContest(currentDateTimeMin,currentDateTime)
        viewmodel.contestDetails.observe(viewLifecycleOwner,{response->
            when(response){
                is Resource.Success->{
                    hideProgressbar()
                    response.data?.let {
                        contestAdapter.differ.submitList(it.contests)
                        Log.d("mytag2",""+it.contests)

                    }
                }

                is Resource.Error->{
                    hideProgressbar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occured : $it",Toast.LENGTH_LONG).show()
                    }

                }
                is Resource.Loading->{
                    showProgressbar()

                }
            }
        })
    }

    private fun initRecyclerView() {
        contestAdapter = ContestAdapter()
        fragmentContestBinding.contestRv.apply{
            adapter = contestAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun showProgressbar(){
        fragmentContestBinding.contestPb.visibility = View.VISIBLE
    }
    private fun hideProgressbar(){
        fragmentContestBinding.contestPb.visibility = View.INVISIBLE
    }
}