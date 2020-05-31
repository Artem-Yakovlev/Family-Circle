package com.tydeya.familycircle.presentation.ui.managerpart.menu

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.BUNDLE_FAMILY_ID
import com.tydeya.familycircle.databinding.DialogAddFamilyMemberToFamilyBinding
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFamilyMemberDialogFragment : DialogFragment() {


    private var _binding: DialogAddFamilyMemberToFamilyBinding? = null
    private val binding get() = _binding!!

    private lateinit var familyViewModelFactory: FamilyViewModelFactory
    private lateinit var familyViewModel: FamilyViewModel

    private lateinit var root: View

    private lateinit var familyId: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater
                .inflate(R.layout.dialog_add_family_member_to_family, null)
        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogAddFamilyMemberToFamilyBinding.bind(root)
        familyId = requireArguments().getString(BUNDLE_FAMILY_ID) ?: ""

        familyViewModelFactory = FamilyViewModelFactory(familyId)
        familyViewModel = ViewModelProviders.of(requireActivity(), familyViewModelFactory)
                .get(FamilyViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputPhoneNumberCcp.registerCarrierNumberEditText(binding.inputPhoneNumberInput)

        binding.actionButton.setOnClickListener {
            if (binding.inputPhoneNumberCcp.isValidFullNumber) {
                GlobalScope.launch(Dispatchers.Default) {
                    familyViewModel.inviteUserToFamily(
                            familyId, binding.inputPhoneNumberCcp.fullNumberWithPlus)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                requireContext(),
                                getString(R.string.add_family_member_success_toast_text),
                                Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                }
            } else {
                binding.inputPhoneNumberInput.error =
                        getString(R.string.start_input_number_invalid_error)
            }
        }

        binding.cancelButton.setOnClickListener { dismiss() }
    }

    companion object {

        val TAG = AddFamilyMemberDialogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(familyId: String) = AddFamilyMemberDialogFragment().apply {
            arguments = bundleOf(BUNDLE_FAMILY_ID to familyId)
        }

    }

}