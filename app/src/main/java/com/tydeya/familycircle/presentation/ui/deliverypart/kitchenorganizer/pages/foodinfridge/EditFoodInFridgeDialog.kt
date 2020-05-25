package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodinfridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.DIALOG_FOOD_OBJECT
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.presentation.viewmodel.FoodInFridgeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditFoodInFridgeDialog : FoodActionDialog() {

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        foodInFridgeViewModel = ViewModelProviders.of(requireParentFragment())
                .get(FoodInFridgeViewModel::class.java)

        return binding.root
    }

    override fun initUi() {
        binding.title.text = requireContext().getString(R.string.edit_product)
        binding.actionButton.text = requireContext().getString(R.string.dialog_new_buy_list_accept)
    }

    override fun action() {
        when (val actualFood = createFoodByInputtedData(food!!.id)) {
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
                        GlobalScope.launch(Dispatchers.Main) {
                            foodInFridgeViewModel.editFoodInFridgeData(actualFood)
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

        @JvmStatic
        fun newInstance(food: Food) = EditFoodInFridgeDialog().apply {
            arguments = Bundle().apply {
                putParcelable(DIALOG_FOOD_OBJECT, food)
            }
        }
    }
}