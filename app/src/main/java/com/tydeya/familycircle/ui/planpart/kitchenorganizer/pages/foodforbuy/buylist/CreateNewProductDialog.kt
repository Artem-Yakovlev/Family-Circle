package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.FoodActionDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNewProductDialog private constructor() : FoodActionDialog() {

    override fun action() {
        val title = binding.productNameInput.text.toString().trim()

        if (title == "") {
            binding.productNameInput.error = requireContext().resources
                    .getString(R.string.empty_necessary_field_warning)
        } else {
            lifecycleScope.launch(Dispatchers.Default) {
                if (!buyCatalogViewModel.isThereInBuysCatalogProductWithName(title)) {
                    buyCatalogViewModel.createProduct(createFoodByInputtedData())
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

    companion object {
        @JvmStatic
        fun newInstance() = CreateNewProductDialog()
    }

}