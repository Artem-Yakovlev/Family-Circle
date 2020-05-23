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
import com.tydeya.familycircle.databinding.DialogEatFoodInFridgeBinding
import com.tydeya.familycircle.presentation.viewmodel.FoodInFridgeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

open class EatFoodFromFridgeDialog : DialogFragment() {

    private var _binding: DialogEatFoodInFridgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    private lateinit var food: Food

    private lateinit var actualAmount: String

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater
                .inflate(R.layout.dialog_eat_food_in_fridge, null)
        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogEatFoodInFridgeBinding.bind(root)
        foodInFridgeViewModel = ViewModelProviders.of(requireParentFragment())
                .get(FoodInFridgeViewModel::class.java)
        food = requireArguments().getParcelable(FOOD_OBJECT)!!
        initActualAmount()
        return binding.root
    }

    private fun initActualAmount() {
        val measure = requireContext().resources
                .getStringArray(R.array.quantitative_type_of_product)[food.measureType.ordinal]

        actualAmount = "${food.quantityOfMeasure} $measure"

        binding.actualAmount.text = requireContext().resources
                .getString(R.string.eat_food_from_fridge_actual_amount_placeholder,
                        food.quantityOfMeasure.toString(), measure)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionButton.setOnClickListener {
            val eatenAmountString = binding.eatenAmountInput.text.toString()
            if (eatenAmountString != "") {
                val eatenAmount = BigDecimal(eatenAmountString)

                if (eatenAmount <= food.quantityOfMeasure) {
                    GlobalScope.launch(Dispatchers.Default) {
                        foodInFridgeViewModel.eatFoodFromFridge(eatenAmount, food)
                        withContext(Dispatchers.Main) {
                            dismiss()
                        }
                    }
                } else {
                    binding.eatenAmountInput.error = resources
                            .getString(R.string.eat_food_from_fridge_actual_amount_error_placeholder,
                                    actualAmount)
                }
            } else {
                binding.eatenAmountInput.error = resources
                        .getString(R.string.empty_necessary_field_warning)
            }
        }
        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        private const val FOOD_OBJECT = "food_object"

        @JvmStatic
        fun newInstance(food: Food) = EatFoodFromFridgeDialog().apply {
            arguments = Bundle().apply {
                putParcelable(FOOD_OBJECT, food)
            }
        }
    }

}