package com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksforuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App

import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksforuser.recyclerview.TasksForUserRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_tasks_for_user.*
import javax.inject.Inject

class TasksForUserFragment : Fragment(R.layout.fragment_tasks_for_user), TasksOrganizerInteractorCallback {

    @Inject
    lateinit var tasksOrganizerInteractor: TasksOrganizerInteractor

    private lateinit var adapter: TasksForUserRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        setAdapter()

    }

    private fun setAdapter() {
        adapter = TasksForUserRecyclerViewAdapter(context!!, ArrayList())
        tasks_for_user_recycler_view.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        tasks_for_user_recycler_view.adapter = adapter
    }

    private fun setCurrentData() {
        adapter.refreshData(tasksOrganizerInteractor.tasksForUser)
    }

    /**
     * Callbacks
     * */

    override fun tasksDataFromServerUpdate() {
        setCurrentData()
    }

    override fun onResume() {
        super.onResume()
        tasksOrganizerInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        tasksOrganizerInteractor.unsubscribe(this)
    }

}
