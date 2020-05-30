package com.tydeya.familycircle.presentation.ui.registrationpart.familyselection

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogCreateNewFamilyAccountBinding
import com.tydeya.familycircle.presentation.viewmodel.FamilySelectionViewModel
import com.tydeya.familycircle.utils.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNewFamilyDialogFragment : DialogFragment() {

    private var _binding: DialogCreateNewFamilyAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var familySelectionViewModel: FamilySelectionViewModel

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater
                .inflate(R.layout.dialog_create_new_family_account, null)
        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = DialogCreateNewFamilyAccountBinding.bind(root)
        familySelectionViewModel = ViewModelProviders
                .of(requireParentFragment()).get(FamilySelectionViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.actionButton.setOnClickListener {

            if (binding.familyNameInput.value == "") {
                binding.familyNameInput.error = getString(R.string.empty_necessary_field_warning)
            } else {
                GlobalScope.launch(Dispatchers.Default) {
                    familySelectionViewModel.createNewFamily(binding.familyNameInput.value)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), getString(R.string.family_account_created),
                                Toast.LENGTH_LONG).show()
                        dismiss()
                    }
                }
            }
        }

        binding.cancelButton.setOnClickListener { dismiss() }
    }

    companion object {
        val TAG = CreateNewFamilyDialogFragment::class.simpleName!!

        fun newInstance() = CreateNewFamilyDialogFragment()
    }

}