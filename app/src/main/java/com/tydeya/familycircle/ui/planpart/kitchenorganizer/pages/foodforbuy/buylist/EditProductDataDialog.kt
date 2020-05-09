package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.utils.value
import com.tydeya.familycircle.viewmodel.BuyCatalogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditProductDataDialog private constructor() : FoodActionDialog() {

    private lateinit var food: Food

    private lateinit var buyCatalogViewModel: BuyCatalogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        buyCatalogViewModel = ViewModelProviders.of(requireParentFragment())
                .get(BuyCatalogViewModel::class.java)

        return binding.root
    }

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
        when (val actualFood = createFoodByInputtedData(food.id)) {
            food -> {
                dismiss()
            }
            else -> {
                when (actualFood.title) {
                    "" -> {
                        binding.productNameInput.error = requireContext().resources
                                .getString(R.string.empty_necessary_field_warning)
                    }
                    else -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            buyCatalogViewModel.editProduct(actualFood)
                            withContext(Dispatchers.Main) {
                                dismiss()
                            }

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