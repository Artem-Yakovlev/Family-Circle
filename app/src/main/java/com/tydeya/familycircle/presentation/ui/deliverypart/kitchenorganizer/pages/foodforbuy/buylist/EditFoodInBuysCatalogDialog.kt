package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.DIALOG_FOOD_OBJECT
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.presentation.viewmodel.kitchen.buycatalogviewmodel.BuyCatalogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class EditFoodInBuysCatalogDialog : FoodActionDialog() {


    private lateinit var buyCatalogViewModel: BuyCatalogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        buyCatalogViewModel = ViewModelProviders.of(requireParentFragment())
                .get(BuyCatalogViewModel::class.java)

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

        @JvmStatic
        fun newInstance(food: Food) = EditFoodInBuysCatalogDialog().apply {
            arguments = Bundle().apply {
                putParcelable(DIALOG_FOOD_OBJECT, food)
            }
        }
    }
}