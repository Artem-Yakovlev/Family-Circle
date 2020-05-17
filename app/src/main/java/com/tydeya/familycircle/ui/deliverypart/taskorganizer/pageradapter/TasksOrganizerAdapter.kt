package com.tydeya.familycircle.ui.deliverypart.taskorganizer.pageradapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tydeya.familycircle.ui.deliverypart.taskorganizer.pages.tasksbyuser.TasksByUserFragment
import com.tydeya.familycircle.ui.deliverypart.taskorganizer.pages.tasksforuser.TasksForUserFragment
import com.tydeya.familycircle.ui.deliverypart.taskorganizer.pages.taskshistory.TasksHistoryFragment

class TasksOrganizerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TasksForUserFragment()
            1 -> TasksByUserFragment()
            2 -> TasksHistoryFragment()
            else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
        }
    }
}