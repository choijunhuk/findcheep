package com.example.myapplication3.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication3.ui.favorite.FavoriteFragment
import com.example.myapplication3.ui.search.SearchFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment()
            1 -> FavoriteFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}