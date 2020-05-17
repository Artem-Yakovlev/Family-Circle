package com.tydeya.familycircle.ui.deliverypart.taskorganizer.pages.tasksforuser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor
import com.tydeya.familycircle.ui.deliverypart.taskorganizer.pages.tasksforuser.recyclerview.TasksForUserRecyclerViewAdapter
import com.tydeya.familycircle.ui.deliverypart.taskorganizer.pages.tasksforuser.recyclerview.TasksForUserRecyclerViewClickListener
import kotlinx.android.synthetic.main.fragment_tasks_for_user.*
import javax.inject.Inject

class TasksForUserFragment
    :
        Fragment(R.layout.fragment_tasks_for_user), TasksOrganizerInteractorCallback,
        TasksForUserRecyclerViewClickListener {

    @Inject
    lateinit var tasksOrganizerInteractor: TasksOrganizerInteractor

    private lateinit var adapter: TasksForUserRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        setAdapter()

    }

    /**
     * Recycler view adapter
     * */

    private fun setAdapter() {
        adapter = TasksForUserRecyclerViewAdapter(context!!, ArrayList(), this)
        tasks_for_user_recycler_view.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        tasks_for_user_recycler_view.adapter = adapter
    }

    private fun setCurrentData() {
        adapter.refreshData(tasksOrganizerInteractor.tasksForUser)
    }

    override fun completeTask(familyTask: FamilyTask) {
        tasksOrganizerInteractor.performTask(familyTask)
    }

    override fun refuseTask(familyTask: FamilyTask) {
        tasksOrganizerInteractor.refuseTask(familyTask)
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
