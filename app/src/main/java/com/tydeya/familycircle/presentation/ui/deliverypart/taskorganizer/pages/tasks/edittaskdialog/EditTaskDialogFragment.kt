package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.edittaskdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.TaskMember
import com.tydeya.familycircle.data.taskorganizer.TaskStatus
import com.tydeya.familycircle.databinding.DialogTasksEditBinding
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.createtaskdialog.dialogtaskworkersrecycler.TaskAddMembersRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModel
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditTaskDialogFragment : DialogFragment() {

    private var _binding: DialogTasksEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var editTaskMembersAdapter: TaskAddMembersRecyclerViewAdapter
    private lateinit var familyViewModel: FamilyViewModel
    private lateinit var tasksViewModel: TasksViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        tasksViewModel = ViewModelProviders
                .of(requireParentFragment(), TasksViewModelFactory(requireActivity().currentFamilyId))
                .get(TasksViewModel::class.java)

        _binding = DialogTasksEditBinding.inflate(
                LayoutInflater.from(requireContext()), container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireArguments().getParcelable<FamilyTask>(TASK)?.let { task ->
            setCurrentData(task)
            initRecyclerView(task)

            binding.editTaskDialogCancelButton.setOnClickListener {
                dismiss()
            }

            binding.editTaskDialogDeleteButton.setOnClickListener {
                tasksViewModel.deleteTask(task.id)
                dismiss()
            }

            binding.editTaskDialogApplyButton.setOnClickListener {
                if (!DataConfirming.isEmptyCheck(binding.editTaskDialogTextInput, true)
                        and !DataConfirming.isEmptyCheck(binding.editTaskDialogTitleInput, true)
                ) {
                    if (editTaskMembersAdapter.members.none(TaskMember::isAdded)) {
                        requireContext().showToast(R.string.create_task_dialog_add_someone_text)
                    } else {
                        GlobalScope.launch(Dispatchers.Default) {
                            tasksViewModel.updateTask(
                                    FamilyTask(
                                            id = task.id,
                                            title = binding.editTaskDialogTitleInput.text.toString(),
                                            text = binding.editTaskDialogTextInput.text.toString(),
                                            workers = editTaskMembersAdapter.members
                                                    .filter(TaskMember::isAdded)
                                                    .map(TaskMember::phoneNumber)
                                                    .toArrayList(),
                                            author = getUserPhone(),
                                            status = TaskStatus.PENDING
                                    )
                            )
                            withContext(Dispatchers.Main) {
                                dismiss()
                            }
                        }
                    }
                }
            }

        } ?: dismiss()
    }

    private fun setCurrentData(task: FamilyTask) {
        binding.editTaskDialogTitleInput.value = task.title
        binding.editTaskDialogTextInput.value = task.text
    }

    private fun initRecyclerView(task: FamilyTask) {
        editTaskMembersAdapter = TaskAddMembersRecyclerViewAdapter()
        binding.editTaskDialogMembersRecyclerview.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.editTaskDialogMembersRecyclerview.adapter = editTaskMembersAdapter
        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer { members ->
            when (members) {
                is Resource.Success -> editTaskMembersAdapter.refreshData(
                        members.data.map(::TaskMember)
                                .map { it.copy(isAdded = it.phoneNumber in task.workers) }
                                .toArrayList()
                )
                is Resource.Failure -> dismiss()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        val TAG = EditTaskDialogFragment::class.java.simpleName
        private const val TASK = "task"

        fun newInstance(task: FamilyTask) = EditTaskDialogFragment().apply {
            arguments = bundleOf(TASK to task)
        }
    }
}