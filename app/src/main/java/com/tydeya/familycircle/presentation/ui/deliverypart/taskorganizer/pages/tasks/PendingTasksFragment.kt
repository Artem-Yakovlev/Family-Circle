package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.databinding.FragmentTasksForUserBinding
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.createtaskdialog.CreateTaskDialogFragment
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.edittaskdialog.EditTaskDialogFragment
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview.TasksRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview.TasksRecyclerViewClickListener
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModel
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.popBackStack

class PendingTasksFragment
    :
        Fragment(R.layout.fragment_tasks_for_user), TasksRecyclerViewClickListener {

    private var _binding: FragmentTasksForUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var familyViewModel: FamilyViewModel
    private lateinit var tasksViewModel: TasksViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTasksForUserBinding.inflate(
                LayoutInflater.from(requireContext()), container, false
        )

        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        tasksViewModel = ViewModelProviders
                .of(requireParentFragment(), TasksViewModelFactory(requireActivity().currentFamilyId))
                .get(TasksViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTasksRecyclerAdapter()
        binding.floatingButton.setOnClickListener {
            CreateTaskDialogFragment.newInstance()
                    .show(parentFragmentManager, CreateTaskDialogFragment.TAG)
        }
    }

    /**
     * Recycler view adapter
     * */

    private fun initTasksRecyclerAdapter() {
        val tasksAdapter = TasksRecyclerViewAdapter(
                listener = this,
                mainTaskStatus = TaskStatus.PENDING
        )
        binding.tasksForUserRecyclerView.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.tasksForUserRecyclerView.adapter = tasksAdapter

        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    tasksAdapter.refreshFamilyMembers(it.data)
                }
                is Resource.Failure -> {
                    popBackStack()
                }
            }
        })

        tasksViewModel.pendingTasksLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    tasksAdapter.refreshTasks(it.data)
                    binding.loadingCircle.visibility = View.GONE
                    binding.tasksForUserRecyclerView.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    binding.loadingCircle.visibility = View.VISIBLE
                    binding.tasksForUserRecyclerView.visibility = View.GONE
                }
                is Resource.Failure -> {
                    popBackStack()
                }
            }
        })
    }

    override fun taskIsChecked(familyTask: FamilyTask) {
        tasksViewModel.updateTask(familyTask.copy(status = TaskStatus.COMPLETED))
    }

    override fun editTask(familyTask: FamilyTask) {
        EditTaskDialogFragment.newInstance(familyTask)
                .show(parentFragmentManager, EditTaskDialogFragment.TAG)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
