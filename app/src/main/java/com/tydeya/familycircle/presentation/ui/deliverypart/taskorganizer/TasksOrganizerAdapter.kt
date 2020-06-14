package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.PendingTasksFragment
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.CompletedTasksFragment

class TasksOrganizerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PendingTasksFragment()
            1 -> CompletedTasksFragment()
            else -> throw (IllegalArgumentException("The adapter is designed for only 2 fragments"))
        }
    }
}