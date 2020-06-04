package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodinfridge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.DialogEatFoodWithoutMeasureTypeInFridgeBinding
import com.tydeya.familycircle.presentation.viewmodel.kitchen.FoodInFridgeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EatFoodWithoutMeasureTypeFromFridge: DialogFragment() {

    private var _binding: DialogEatFoodWithoutMeasureTypeInFridgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    private lateinit var food: Food

    private lateinit var actualAmount: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater
                .inflate(R.layout.dialog_eat_food_without_measure_type_in_fridge, null)
        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogEatFoodWithoutMeasureTypeInFridgeBinding.bind(root)
        foodInFridgeViewModel = ViewModelProviders.of(requireParentFragment())
                .get(FoodInFridgeViewModel::class.java)
        food = requireArguments().getParcelable(FOOD_OBJECT)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Default) {
                foodInFridgeViewModel.deleteFromFridgeEatenFood(food.id)
                withContext(Dispatchers.Main) {
                    dismiss()
                }
            }
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val FOOD_OBJECT = "food_object"

        @JvmStatic
        fun newInstance(food: Food) = EatFoodWithoutMeasureTypeFromFridge().apply {
            arguments = Bundle().apply {
                putParcelable(FOOD_OBJECT, food)
            }
        }
    }

}