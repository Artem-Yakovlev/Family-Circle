package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.utils.value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProductDataDialog private constructor() : FoodActionDialog() {

    private lateinit var food: Food

    override fun initUi() {
        super.initUi()
        binding.title.text = requireContext().getString(R.string.edit_product)
        binding.actionButton.text = requireContext().getString(R.string.dialog_new_buy_list_accept)
    }

    override fun fillUiWithCurrentData() {
        super.fillUiWithCurrentData()
        food = requireArguments().getParcelable(FOOD_OBJECT)!!
        binding.productNameInput.value = food.title
        if (food.measureType != MeasureType.NOT_CHOSEN) {
            binding.numberOfProductsInMeasureInput.value = food.quantityOfMeasure.toString()
            binding.measureSpinner.setSelection(food.measureType.ordinal)
        }
    }

    override fun action() {
        when (val title = binding.productNameInput.text.toString().trim()) {
            food.title -> {
                dismiss()
            }
            "" -> {
                binding.productNameInput.error = requireContext()
                        .resources.getString(R.string.empty_necessary_field_warning)
            }
            else -> {
                lifecycleScope.launch(Dispatchers.Main) {
                    if (buyCatalogViewModel.isThereInBuysCatalogProductWithName(title)) {
                        withContext(Dispatchers.Main) {
                            binding.productNameInput.error = requireContext().resources
                                    .getString(R.string.dialog_edit_food_data_already_exist)
                        }
                    } else {
                        buyCatalogViewModel.editProduct(food.title, title)
                        withContext(Dispatchers.Main) {
                            dismiss()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val FOOD_OBJECT = "food_object"

        @JvmStatic
        fun newInstance(food: Food) = EditProductDataDialog().apply {
            arguments = Bundle().apply {
                putParcelable(FOOD_OBJECT, food)
            }
        }
    }
}