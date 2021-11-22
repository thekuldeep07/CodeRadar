package com.example.coderadar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.CodeRadar.databinding.FragmentContestBinding
import com.example.coderadar.contestTabs.*
import com.example.coderadar.presentation.adapter.TabsAdapter


class ContestFragment : Fragment() {
    private lateinit var  fragmentContestBinding: FragmentContestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentContestBinding = FragmentContestBinding.inflate(inflater, container, false)
        mainFunction()
        return fragmentContestBinding.root
    }

    private fun mainFunction() {

        fragmentContestBinding.tabs.setupWithViewPager(fragmentContestBinding.viewPager)

        val tabAdapter = TabsAdapter(activity?.supportFragmentManager!!)
        tabAdapter.addFragment(TabFragment1(), "CodeChef")
        tabAdapter.addFragment(TabFragment2(), "LeetCode")
        tabAdapter.addFragment(TabFragment3(), "HackerRank")
        tabAdapter.addFragment(TabFragment4(), "HackerEarth")
        tabAdapter.addFragment(TabFragment5(), "CodeForces")
        tabAdapter.addFragment(TabFragment6(), "Google")
        fragmentContestBinding.viewPager.adapter = tabAdapter
    }

}