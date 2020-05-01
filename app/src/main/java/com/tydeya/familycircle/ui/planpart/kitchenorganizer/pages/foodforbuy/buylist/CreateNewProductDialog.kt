package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.utils.value
import com.tydeya.familycircle.viewmodel.BuyCatalogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNewProductDialog : DialogFragment() {

    private lateinit var buyCatalogViewModel: BuyCatalogViewModel

    private var _binding: DialogKitchenOrganizerFoodActionBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity()
                .layoutInflater.inflate(R.layout.dialog_kitchen_organizer_food_action, null)
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
        initMeasureSpinner()
        initActionButtons()
    }

    private fun initMeasureSpinner() {
        ArrayAdapter.createFromResource(
                requireContext(),
                R.array.quantitative_type_of_product,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.measureSpinner.adapter = adapter
            binding.measureSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (position == 0) {
                        binding.numberOfProductsInMeasureInput.value = ""
                        binding.numberOfProductsInMeasureInput.isEnabled = false
                    } else {
                        binding.numberOfProductsInMeasureInput.isEnabled = true
                    }
                }
            }
        }
    }

    private fun initActionButtons() {
        binding.actionButton.setOnClickListener {
            val title = binding.productNameInput.text.toString().trim()

            if (title == "") {
                binding.productNameInput.error = requireContext().resources
                        .getString(R.string.empty_necessary_field_warning)
            } else {
                lifecycleScope.launch(Dispatchers.Default) {
                    if (!buyCatalogViewModel.isThereInBuysCatalogProductWithName(title)) {
                        buyCatalogViewModel.createProduct(createFoodByInputtedData(title))
                        withContext(Dispatchers.Main) {
                            dismiss()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            binding.productNameInput.error = requireContext().resources
                                    .getString(R.string.dialog_edit_food_data_already_exist)
                        }
                    }
                }
            }
        }
        binding.cancelButton.setOnClickListener { dismiss() }
    }

    private fun createFoodByInputtedData(title: String) = Food("", title, FoodStatus.NEED_BUY,
            when (binding.numberOfProductsInMeasureInput.text.toString().trim()) {
                "" -> .0
                else -> binding.numberOfProductsInMeasureInput.text.toString().trim().toDouble()
            }, MeasureType.values()[binding.measureSpinner.selectedItemPosition])
}