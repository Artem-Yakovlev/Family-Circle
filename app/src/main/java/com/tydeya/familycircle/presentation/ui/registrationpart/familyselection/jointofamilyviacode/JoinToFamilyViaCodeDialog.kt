package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.jointofamilyviacode

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogJoinToFamilyViaCodeBinding
import com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.jointofamilyviacode.recyclerview.FamilyInviteCodeOnClickListener
import com.tydeya.familycircle.presentation.ui.registrationpart.familyselection.jointofamilyviacode.recyclerview.FamilyInvitesRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.FamilySelectionViewModel
import com.tydeya.familycircle.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JoinToFamilyViaCodeDialog : DialogFragment(), FamilyInviteCodeOnClickListener {

    private var _binding: DialogJoinToFamilyViaCodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var familySelectionViewModel: FamilySelectionViewModel

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater
                .inflate(R.layout.dialog_join_to_family_via_code, null)
        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = DialogJoinToFamilyViaCodeBinding.bind(root)
        familySelectionViewModel = ViewModelProviders
                .of(requireParentFragment()).get(FamilySelectionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cancelButton.setOnClickListener { dismiss() }

        val adapter = FamilyInvitesRecyclerViewAdapter(ArrayList(), this)
        binding.dialogSelectionPageInvitesRecyclerview.adapter = adapter
        binding.dialogSelectionPageInvitesRecyclerview.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        familySelectionViewModel.inviteCodes.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    adapter.refreshData(it.data)
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    dismiss()
                }
            }
        })

    }

    override fun acceptCode(inviteCode: String) {
        GlobalScope.launch(Dispatchers.Default) {
            familySelectionViewModel.joinToFamily(inviteCode)
        }
    }

    override fun refuseCode(inviteCode: String) {
        GlobalScope.launch(Dispatchers.Default) {
            familySelectionViewModel.refuseInvite(inviteCode)
        }
    }

    companion object {
        val TAG = JoinToFamilyViaCodeDialog::class.simpleName!!

        fun newInstance() = JoinToFamilyViaCodeDialog()
    }

}
