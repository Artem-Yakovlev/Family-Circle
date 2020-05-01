package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.utils.value
import com.tydeya.familycircle.viewmodel.BuyCatalogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProductDataDialog private constructor() : DialogFragment() {

    private lateinit var actualTitle: String

    private lateinit var buyCatalogViewModel: BuyCatalogViewModel

    private var _binding: DialogKitchenOrganizerFoodActionBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater
                .inflate(R.layout.dialog_kitchen_organizer_food_action, null)
        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        buyCatalogViewModel = ViewModelProviders.of(requireParentFragment())
                .get(BuyCatalogViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actualTitle = requireArguments().getString(ACTUAL_PRODUCT_TITLE, "")

        binding.actionButton.setOnClickListener {
            when (val title = binding.productNameInput.text.toString().trim()) {
                actualTitle -> {
                    dismiss()
                }
                "" -> {
                    binding.productNameInput.error = view.context!!
                            .resources.getString(R.string.empty_necessary_field_warning)
                }
                else -> {
                    lifecycleScope.launch(Dispatchers.Main) {
                        if (buyCatalogViewModel.isThereInBuysCatalogProductWithName(title)) {
                            withContext(Dispatchers.Main) {
                                binding.productNameInput.error = view.context!!.resources
                                        .getString(R.string.dialog_edit_food_data_already_exist)
                            }
                        } else {
                            buyCatalogViewModel.editProduct(actualTitle, title)
                            withContext(Dispatchers.Main) {
                                dismiss()
                            }
                        }
                    }
                }
            }
        }

        binding.cancelButton.setOnClickListener { dismiss() }
        binding.productNameInput.value = actualTitle
    }

    companion object {

        private const val ACTUAL_PRODUCT_TITLE = "actual_product_title"

        @JvmStatic
        fun newInstance(actualTitle: String) = EditProductDataDialog().apply {
            arguments = Bundle().apply {
                putString(ACTUAL_PRODUCT_TITLE, actualTitle)
            }
        }
    }
}