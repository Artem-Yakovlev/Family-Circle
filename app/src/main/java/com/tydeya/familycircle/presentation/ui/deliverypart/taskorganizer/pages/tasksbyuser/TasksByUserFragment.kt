package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.domain.taskorganizer.interactor.abstraction.TasksOrganizerInteractorCallback
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.recyclerview.TasksByUserRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.recyclerview.TasksByUserRecyclerViewOnClickListener
import kotlinx.android.synthetic.main.fragment_tasks_by_user.*
import javax.inject.Inject

class TasksByUserFragment
    :
        Fragment(R.layout.fragment_tasks_by_user), TasksOrganizerInteractorCallback,
        TasksByUserRecyclerViewOnClickListener {

    @Inject
    lateinit var tasksOrganizerInteractor: TasksOrganizerInteractor

    private lateinit var adapter: TasksByUserRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        setAdapter()
        setFloatingButton()
    }

    private fun setAdapter() {
        adapter = TasksByUserRecyclerViewAdapter(context!!, ArrayList(), this)
        tasks_by_user_recycler_view.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        tasks_by_user_recycler_view.adapter = adapter
    }

    private fun setFloatingButton() {
        create_task_floating_button.setOnClickListener {
            val dialog = CreateTaskDialog()
            dialog.show(parentFragmentManager, "dialog_create_task")
        }

        create_task_floating_button.attachToRecyclerView(tasks_by_user_recycler_view)
    }

    private fun setCurrentData() {
        adapter.refreshData(tasksOrganizerInteractor.tasksByUser)
    }

    /**
     * On recycler item click listeners
     * */

    override fun editEvent(familyTask: FamilyTask) {
        val editDialog = EditTasksTextDialog(familyTask)
        editDialog.show(parentFragmentManager, "editTasksTextDialog")
    }

    override fun deleteEvent(familyTask: FamilyTask) {
        tasksOrganizerInteractor.deleteTask(familyTask)
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
