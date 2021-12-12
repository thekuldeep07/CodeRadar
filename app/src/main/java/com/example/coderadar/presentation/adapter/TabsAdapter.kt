package com.example.coderadar.presentation.adapter

import android.content.Context
import android.icu.text.CaseMap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class TabsAdapter (fm: FragmentManager):FragmentStatePagerAdapter(fm) {
    private val fragmentArray = ArrayList<Fragment>()
    private val stringArray = ArrayList<String>()
    override fun getCount(): Int {
        return fragmentArray.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentArray[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentArray.add(fragment)
        stringArray.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return stringArray[position]
    }

}