package com.example.coderadar.contestTabs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentTab1Binding
import com.example.coderadar.data.util.Resource
import com.example.coderadar.presentation.adapter.ContestAdapter
import com.example.coderadar.presentation.viewmodel.ContestsViewModel
import com.example.coderadar.ui.ContestActivity
import java.time.LocalDateTime

class TabFragment1 : Fragment() {
    private lateinit var viewmodel: ContestsViewModel
    private lateinit var  tab1Binding: FragmentTab1Binding
    private lateinit var contestAdapter: ContestAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab1Binding = FragmentTab1Binding.bind(view)
        viewmodel = (activity as ContestActivity).viewModel
        initRecyclerView()
        viewNewsList()


    }

    private fun viewNewsList() {
        showProgressbar()
        val currentDateTimeMin = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0)
        val currentDateTime = LocalDateTime.now()
        Log.d("date",""+currentDateTime)
        val resource = "codechef.com"
        val responseLiveData = viewmodel.getContest(resource,currentDateTimeMin,currentDateTime)
        responseLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it!=null){
                contestAdapter.setList(it)
                contestAdapter.notifyDataSetChanged()
                hideProgressbar()
            }
            else{
             hideProgressbar()
                Toast.makeText(context,"codechef No data available", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun initRecyclerView() {
        contestAdapter = ContestAdapter()
        tab1Binding.contestRv1.apply{
            adapter = contestAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun showProgressbar(){
        tab1Binding.contestPb1.visibility = View.VISIBLE
    }
    private fun hideProgressbar(){
        tab1Binding.contestPb1.visibility = View.INVISIBLE
    }
}