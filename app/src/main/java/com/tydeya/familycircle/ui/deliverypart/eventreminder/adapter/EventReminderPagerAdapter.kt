package com.tydeya.familycircle.ui.deliverypart.eventreminder.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tydeya.familycircle.ui.deliverypart.eventreminder.pages.EventRibbonFragment
import com.tydeya.familycircle.ui.deliverypart.eventreminder.pages.EventReminderCalendarFragment

class EventReminderPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventRibbonFragment()
            1 -> EventReminderCalendarFragment()
            else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
        }
    }
}