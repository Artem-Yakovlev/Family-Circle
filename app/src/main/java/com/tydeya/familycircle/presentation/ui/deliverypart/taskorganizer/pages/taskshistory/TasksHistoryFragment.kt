package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.taskshistory

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.taskshistory.recyclerview.HistoryTasksRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_tasks_history.*
import javax.inject.Inject

class TasksHistoryFragment : Fragment(R.layout.fragment_tasks_history), TasksOrganizerInteractorCallback {

    @Inject
    lateinit var tasksOrganizerInteractor: TasksOrganizerInteractor

    private lateinit var adapter: HistoryTasksRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        setAdapter()
    }

    private fun setAdapter() {
        adapter = HistoryTasksRecyclerViewAdapter(context!!, ArrayList())
        history_tasks_recyclerview.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        history_tasks_recyclerview.adapter = adapter
    }

    private fun setCurrentData() {
        adapter.refreshData(tasksOrganizerInteractor.sortedHistoryTasks)
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