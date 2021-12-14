package com.example.coderadar.contestTabs

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentTab1Binding
import com.example.coderadar.mvvm.LoginViewModel
import com.example.coderadar.presentation.adapter.ContestAdapter
import com.example.coderadar.presentation.viewmodel.ContestsViewModel
import com.example.coderadar.ui.ContestActivity
import java.time.LocalDateTime


class TabFragment1 : Fragment() {
    private lateinit var viewmodel: ContestsViewModel
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var  tab1Binding: FragmentTab1Binding
    private lateinit var contestAdapter: ContestAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_tab1, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tab1Binding = FragmentTab1Binding.bind(view)
        viewmodel = (activity as ContestActivity).viewModel

        loginViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application))
            .get(LoginViewModel::class.java)

        initRecyclerView()
        viewNewsList()



    }

    private fun viewNewsList() {
        showProgressbar()
        val currentDateTimeMin = LocalDateTime.now().minusWeeks(2).withHour(0).withMinute(0).withSecond(0)
        val currentDateTime = LocalDateTime.now()
        Log.i("date", "" + currentDateTimeMin)
        val resource = "codechef.com"
        val responseLiveData = viewmodel.getContest(resource, currentDateTimeMin, currentDateTime)
        responseLiveData.observe(this.viewLifecycleOwner, Observer {
            if (it != null) {
                tab1Binding.errorLayout.visibility=View.GONE
                contestAdapter.setList(it)
                contestAdapter.notifyDataSetChanged()
                if(it.isEmpty()){
                    tab1Binding.noDataLayout.visibility=View.VISIBLE

                }
                else{
                    tab1Binding.contestRv1.visibility=View.VISIBLE
                }


                hideProgressbar()
            } else {
                hideProgressbar()
                tab1Binding.contestRv1.visibility=View.GONE
                tab1Binding.errorLayout.visibility=View.VISIBLE
            }
        })

    }

    private fun initRecyclerView() {
        contestAdapter = ContestAdapter(requireActivity())
        tab1Binding.contestRv1.apply{
            adapter = contestAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
    private fun showProgressbar(){
        tab1Binding.contestPb1.visibility = View.VISIBLE
        tab1Binding.contestRv1.visibility = View.GONE
        tab1Binding.errorLayout.visibility =View.GONE
        tab1Binding.noDataLayout.visibility=View.GONE
    }
    private fun hideProgressbar(){
        tab1Binding.contestPb1.visibility = View.INVISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get item id to handle item clicks
        val id = item.itemId
        //handle item clicks
        if (id == R.id.refresh){
            refresh()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() {
        showProgressbar()
        val currentDateTimeMin = LocalDateTime.now().minusWeeks(2).withHour(0).withMinute(0).withSecond(0)
        val currentDateTime = LocalDateTime.now()
        Log.d("date", "" + currentDateTime)
        val resource = "codechef.com"
        val responseLiveData = viewmodel.updateContest(resource, currentDateTimeMin, currentDateTime)
        responseLiveData.observe(this.viewLifecycleOwner, Observer {
            if (it != null) {
                tab1Binding.errorLayout.visibility=View.GONE
                contestAdapter.setList(it)
                contestAdapter.notifyDataSetChanged()
                if(it.isEmpty()){
                    tab1Binding.noDataLayout.visibility=View.VISIBLE

                }
                else{
                    tab1Binding.contestRv1.visibility=View.VISIBLE
                }


                hideProgressbar()
            } else {
                hideProgressbar()
                tab1Binding.contestRv1.visibility=View.GONE
                tab1Binding.errorLayout.visibility=View.VISIBLE
            }
        })
    }

}