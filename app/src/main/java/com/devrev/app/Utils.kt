package com.devrev.app

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

object Utils{
    /**
     * Extension function to control the scroll sensitivity for the viewpager.
     */
    fun ViewPager2.reduceDragSensitivity(f: Int) {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(this) as RecyclerView

        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop * f)       // "8" was obtained experimentally
    }
}
