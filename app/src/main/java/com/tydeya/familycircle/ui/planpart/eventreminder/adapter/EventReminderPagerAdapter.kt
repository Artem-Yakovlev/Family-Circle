package com.tydeya.familycircle.ui.planpart.eventreminder.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tydeya.familycircle.ui.planpart.eventreminder.pages.EventReminderAllEventsFragment
import com.tydeya.familycircle.ui.planpart.eventreminder.pages.EventReminderCalendarFragment

class EventReminderPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> EventReminderAllEventsFragment()
            1 -> EventReminderCalendarFragment()
            else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
        }
    }
}