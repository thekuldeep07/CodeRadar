package com.example.coderadar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.CodeRadar.R
import com.example.coderadar.data.util.Resource
import com.example.CodeRadar.databinding.FragmentContestBinding
import com.example.coderadar.mvvm.LoginViewModel
import com.example.coderadar.presentation.adapter.ContestAdapter
import com.example.coderadar.presentation.viewmodel.ContestsViewModel
import com.example.coderadar.ui.ContestActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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
        val currentDateTime = LocalDateTime.now().plusSeconds(59)
        Log.d("date",""+currentDateTime)
        viewmodel.getContest(currentDateTime)
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