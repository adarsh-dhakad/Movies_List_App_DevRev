package com.devrev.app.ui.fragments.dashboard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.devrev.app.ui.fragments.movie_list.MovieListFragment

class DashBoardPagerAdapter(val fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val NUM_PAGES = 2

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {

                return getLatestListFragment()
            }

            1 -> {

                return getTopListFragment()
            }

            else ->{
                return getLatestListFragment()
            }
        }
    }


    private fun getLatestListFragment() = MovieListFragment.newInstance(0)

    private fun getTopListFragment() = MovieListFragment.newInstance(1)
}