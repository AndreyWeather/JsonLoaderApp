package com.example.jsonloader

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class viewPagerAdapter(f_activity: FragmentActivity, private val list: List<Fragment>) :
    FragmentStateAdapter(f_activity) {
    val loader = Loader()
    override fun getItemCount(): Int {
        return 50

    }

    override fun createFragment(position: Int): Fragment {

        return Fragment1.newInstance(position)
    }
}