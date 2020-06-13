package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.createtaskdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.TaskMember
import com.tydeya.familycircle.databinding.DialogTasksCreateBinding
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasks.createtaskdialog.dialogtaskworkersrecycler.CreateTaskMembersRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModel
import com.tydeya.familycircle.presentation.viewmodel.tasks.TasksViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.showToast
import com.tydeya.familycircle.utils.extensions.toArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CreateTaskDialogFragment : DialogFragment() {

    private var _binding: DialogTasksCreateBinding? = null
    private val binding get() = _binding!!

    private lateinit var createTaskMembersAdapter: CreateTaskMembersRecyclerViewAdapter
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

        _binding = DialogTasksCreateBinding.inflate(
                LayoutInflater.from(requireContext()), container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddMembersRecyclerView()
        binding.createTaskDialogCancelButton.setOnClickListener {
            dismiss()
        }
        binding.createTaskDialogCreateButton.setOnClickListener {
            if (!DataConfirming.isEmptyCheck(binding.createTaskDialogTitleInput, true)
                    and !DataConfirming.isEmptyCheck(binding.createTaskDialogTextInput, true)
            ) {
                if (createTaskMembersAdapter.members.none(TaskMember::isAdded)) {
                    requireContext().showToast(R.string.create_task_dialog_add_someone_text)
                } else {
                    GlobalScope.launch(Dispatchers.Main) {
                        tasksViewModel.createTask(
                                title = binding.createTaskDialogTitleInput.text.toString(),
                                description = binding.createTaskDialogTextInput.text.toString(),
                                workers = createTaskMembersAdapter.members
                                        .filter(TaskMember::isAdded)
                                        .map(TaskMember::phoneNumber)
                                        .toArrayList()
                        )
                    }
                    dismiss()
                }
            }
        }
    }

    private fun initAddMembersRecyclerView() {
        createTaskMembersAdapter = CreateTaskMembersRecyclerViewAdapter()
        binding.createTaskDialogWorkersRecyclerview.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        )
        binding.createTaskDialogWorkersRecyclerview.adapter = createTaskMembersAdapter
        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer { members ->
            when (members) {
                is Resource.Success -> createTaskMembersAdapter.refreshData(
                        members.data.map(::TaskMember).toArrayList()
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

        val TAG = CreateTaskDialogFragment::class.java.simpleName

        fun newInstance() = CreateTaskDialogFragment()
    }

}
