package com.devrev.app.ui.fragments.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.devrev.app.Utils.reduceDragSensitivity
import com.devrev.app.databinding.FragmentDashboardBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


/**
 *  this is dashboard with two tabs
 */
class DashboardFragment :Fragment(){
    private lateinit var dashBoardPagerAdapter: DashBoardPagerAdapter
    private var selectedIndex = 0

    private lateinit var binding: FragmentDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashBoardPagerAdapter = DashBoardPagerAdapter(requireActivity())
        binding.dashboardViewPager.adapter = dashBoardPagerAdapter
        binding.dashboardViewPager.reduceDragSensitivity(2)
        binding.dashboardViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        }
        )
        TabLayoutMediator(
            binding.brandingTab, binding.dashboardViewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Latest Movies"
                }
                1 -> {
                    tab.text = "Popular Movies"
                }
            }
        }.attach()
        binding.dashboardViewPager.isUserInputEnabled = false
        binding.brandingTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                selectedIndex = tab?.position ?: 0
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }
}