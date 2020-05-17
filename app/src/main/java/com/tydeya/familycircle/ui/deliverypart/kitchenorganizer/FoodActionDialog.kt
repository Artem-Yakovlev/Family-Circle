package com.tydeya.familycircle.ui.deliverypart.kitchenorganizer

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.DIALOG_FOOD_OBJECT
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.utils.value
import java.math.BigDecimal
import java.util.*

abstract class FoodActionDialog protected constructor() : DialogFragment() {

    protected var _binding: DialogKitchenOrganizerFoodActionBinding? = null
    protected val binding get() = _binding!!

    protected lateinit var root: View

    protected var shelfLifeTimestamp = -1L

    protected var food: Food? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater
                .inflate(R.layout.dialog_kitchen_organizer_food_action, null)
        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    abstract override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                       savedInstanceState: Bundle?): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initShelfLifePicker()
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

    private fun initShelfLifePicker() {
        val datePickerDialog = object : DatePickerDialog(requireContext()) {

            override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
                super.onDateChanged(view, year, month, dayOfMonth)
                val shelfLifeCalendar = GregorianCalendar(year, month, dayOfMonth)
                binding.choiceShelfLifeButton.text = getString(R.string
                        .product_shelf_life_input_button_picked_placeholder,
                        DateRefactoring.getDateLocaleText(shelfLifeCalendar))
                shelfLifeTimestamp = shelfLifeCalendar.timeInMillis
            }

            override fun cancel() {
                super.cancel()
                shelfLifeTimestamp = -1L
                binding.choiceShelfLifeButton.text = requireContext()
                        .resources.getString(R.string.product_shelf_life_input_button)
            }

        }
        binding.choiceShelfLifeButton.setOnClickListener {
            datePickerDialog.show()
        }
    }

    /**
     * For overriding in heirs
     * */

    open fun initUi() {
        // stub
    }

    protected open fun fillUiWithCurrentData() {
        food = requireArguments().getParcelable(DIALOG_FOOD_OBJECT)
        food?.let {
            binding.productNameInput.value = it.title

            if (it.measureType != MeasureType.NOT_CHOSEN) {
                binding.numberOfProductsInMeasureInput.value = it.quantityOfMeasure.toString()
                binding.measureSpinner.setSelection(it.measureType.ordinal)
            }

            if (it.shelfLifeTimeStamp == -1L) {
                binding.choiceShelfLifeButton.text = getString(R.string.product_shelf_life_input_button)
            } else {
                shelfLifeTimestamp = it.shelfLifeTimeStamp
                val calendar = GregorianCalendar()
                calendar.timeInMillis = it.shelfLifeTimeStamp
                binding.choiceShelfLifeButton.text = getString(R.string
                        .product_shelf_life_input_button_picked_placeholder,
                        DateRefactoring.getDateLocaleText(calendar))
            }
        }

    }

    abstract fun action()

    protected open fun cancel() {
        dismiss()
    }

    /**
     * Create product from actual data
     * */

    protected fun createFoodByInputtedData(productId: String = ""): Food {
        return Food(productId, binding.productNameInput.text.toString().trim(), FoodStatus.NEED_BUY,
                when (binding.numberOfProductsInMeasureInput.text.toString().trim()) {
                    "" -> BigDecimal.valueOf(0)
                    else -> BigDecimal(binding.numberOfProductsInMeasureInput.text.toString())
                }, MeasureType.values()[binding.measureSpinner.selectedItemPosition],
                shelfLifeTimestamp)
    }

}