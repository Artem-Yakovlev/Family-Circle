package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.buylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.presentation.viewmodel.BuyCatalogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNewProductDialog private constructor() : FoodActionDialog() {

    private lateinit var buyCatalogViewModel: BuyCatalogViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        buyCatalogViewModel = ViewModelProviders.of(requireParentFragment())
                .get(BuyCatalogViewModel::class.java)

        return binding.root
    }

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