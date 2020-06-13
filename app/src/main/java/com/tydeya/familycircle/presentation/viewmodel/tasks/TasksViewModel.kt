package com.tydeya.familycircle.presentation.viewmodel.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.domain.tasks.eventlistener.TasksNetworkListener
import com.tydeya.familycircle.domain.tasks.eventlistener.TasksNetworkListenerCallback
import com.tydeya.familycircle.utils.Resource

class TasksViewModel(
        private val familyId: String
) :
        ViewModel(), TasksNetworkListenerCallback {

    private val pendingTasksMutableLiveData = MutableLiveData<Resource<ArrayList<FamilyTask>>>()
    val pendingTasksLiveData: LiveData<Resource<ArrayList<FamilyTask>>>
        get() = pendingTasksMutableLiveData

    private val completedTasksMutableLiveData = MutableLiveData<Resource<ArrayList<FamilyTask>>>()
    val completedTasksLiveData: LiveData<Resource<ArrayList<FamilyTask>>>
        get() = completedTasksMutableLiveData

    private val tasksNetworkListener = TasksNetworkListener(familyId, this)

    init {
        tasksNetworkListener.register()
    }

    override fun onCleared() {
        super.onCleared()
        tasksNetworkListener.unregister()
    }

    override fun tasksDataUpdated(
            pendingTasks: Resource<ArrayList<FamilyTask>>,
            completedTasks: Resource<ArrayList<FamilyTask>>
    ) {
        pendingTasksMutableLiveData.value = pendingTasks
        completedTasksMutableLiveData.value = completedTasks
    }

}
