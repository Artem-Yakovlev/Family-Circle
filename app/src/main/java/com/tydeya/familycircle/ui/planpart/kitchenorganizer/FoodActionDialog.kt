package com.tydeya.familycircle.ui.planpart.kitchenorganizer

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
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.utils.value
import com.tydeya.familycircle.viewmodel.BuyCatalogViewModel

abstract class FoodActionDialog protected constructor() : DialogFragment() {

    protected lateinit var buyCatalogViewModel: BuyCatalogViewModel

    private var _binding: DialogKitchenOrganizerFoodActionBinding? = null
    protected val binding get() = _binding!!

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
        initUi()
        initMeasureSpinner()
        fillUiWithCurrentData()
        binding.actionButton.setOnClickListener { action() }
        binding.cancelButton.setOnClickListener { cancel() }
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

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int,
                                            id: Long) {
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

    /**
     * For overriding in heirs
     * */

    open fun initUi() {
        //stub
    }

    open fun fillUiWithCurrentData() {
        //stub
    }

    abstract fun action()

    protected open fun cancel() {
        dismiss()
    }

    /**
     * Create product from actual data
     * */

    protected fun createFoodByInputtedData() = Food("",
            binding.productNameInput.text.toString().trim(), FoodStatus.NEED_BUY,
            when (binding.numberOfProductsInMeasureInput.text.toString().trim()) {
                "" -> .0
                else -> binding.numberOfProductsInMeasureInput.text.toString().trim().toDouble()
            }, MeasureType.values()[binding.measureSpinner.selectedItemPosition])

}