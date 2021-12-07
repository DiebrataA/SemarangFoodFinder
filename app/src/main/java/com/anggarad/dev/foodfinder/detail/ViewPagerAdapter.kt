package com.anggarad.dev.foodfinder.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, data: Bundle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private val numberOfPages = 2
    private var fBundle: Bundle = data

    override fun getItemCount(): Int {
        return numberOfPages
    }


    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = DetailsRestoFragment()
            1 -> fragment = ReviewFragment()
        }
        fragment?.arguments = this.fBundle
        return fragment as Fragment
    }
}