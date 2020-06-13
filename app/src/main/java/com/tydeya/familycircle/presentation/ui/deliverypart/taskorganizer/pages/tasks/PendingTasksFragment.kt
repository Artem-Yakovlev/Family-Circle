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
import com.tydeya.familycircle.databinding.FragmentTasksForUserBinding
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview.TasksRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.tasksrecyclerview.TasksRecyclerViewClickListener
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModel
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import kotlinx.android.synthetic.main.fragment_tasks_for_user.*

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
    }

    /**
     * Recycler view adapter
     * */

    private fun initTasksRecyclerAdapter() {
        val tasksAdapter = TasksRecyclerViewAdapter(listener = this)
        tasks_for_user_recycler_view.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        )
        tasks_for_user_recycler_view.adapter = tasksAdapter

        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> tasksAdapter.refreshFamilyMembers(it.data)
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                }
            }
        })

        tasksViewModel.completedTasksLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> tasksAdapter.refreshTasks(it.data)
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                }
            }
        })
    }

    override fun taskIsChecked(isChecked: Boolean) {

    }

    override fun removeTask() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
