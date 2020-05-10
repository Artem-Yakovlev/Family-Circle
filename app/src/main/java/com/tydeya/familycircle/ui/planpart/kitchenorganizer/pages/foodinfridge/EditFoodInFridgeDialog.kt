package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.MeasureType
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.utils.value
import com.tydeya.familycircle.viewmodel.FoodInFridgeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class EditFoodInFridgeDialog : FoodActionDialog() {

    private lateinit var food: Food

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        foodInFridgeViewModel = ViewModelProviders.of(requireParentFragment())
                .get(FoodInFridgeViewModel::class.java)

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

        if (food.shelfLifeTimeStamp == -1L) {
            binding.choiceShelfLifeButton.text = getString(R.string.product_shelf_life_input_button)
        } else {
            val calendar = GregorianCalendar()
            calendar.timeInMillis = food.shelfLifeTimeStamp
            binding.choiceShelfLifeButton.text = getString(R.string
                    .product_shelf_life_input_button_picked_placeholder,
                    DateRefactoring.getDateLocaleText(calendar))
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
        private const val FOOD_OBJECT = "food_object"

        @JvmStatic
        fun newInstance(food: Food) = EditFoodInFridgeDialog().apply {
            arguments = Bundle().apply {
                putParcelable(FOOD_OBJECT, food)
            }
        }
    }
}