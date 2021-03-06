package com.tydeya.familycircle.ui.planpart.taskorganizer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tydeya.familycircle.R
import com.tydeya.familycircle.ui.planpart.taskorganizer.pageradapter.TasksOrganizerAdapter
import kotlinx.android.synthetic.main.fragment_tasks_organizer_main.*

class TasksOrganizerMainFragment : Fragment(R.layout.fragment_tasks_organizer_main) {

    private lateinit var tasksOrganizerAdapter: TasksOrganizerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasksOrganizerAdapter = TasksOrganizerAdapter(this)
        viewPager = task_organizer_pager
        viewPager.adapter = tasksOrganizerAdapter

        TabLayoutMediator(task_organizer_main_tab_layout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tasks_organizer_errands_for_you)
                1 -> getString(R.string.tasks_organizer_your_errands)
                2 -> getString(R.string.tasks_organizer_errands_history)
                else -> throw (IllegalArgumentException("The adapter is designed for only 3 fragments"))
            }
        }.attach()
    }

}
