package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.viewmodel.FoodInFridgeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddFoodInFridgeManuallyDialog : FoodActionDialog() {

    private lateinit var barcodeScannerViewModel: FoodInFridgeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        barcodeScannerViewModel = ViewModelProviders.of(requireActivity())
                .get(FoodInFridgeViewModel::class.java)
        return binding.root
    }

    override fun action() {
        val title = binding.productNameInput.text.toString().trim()
        if (title == "") {
            binding.productNameInput.error = requireContext().resources
                    .getString(R.string.empty_necessary_field_warning)
        } else {
            GlobalScope.launch(Dispatchers.Default) {

                barcodeScannerViewModel.addNewFoodInFridge(createFoodByInputtedData())

                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), resources
                            .getString(R.string.barcode_scanner_activity_product_added),
                            Toast.LENGTH_LONG).show()
                    dismiss()
                }
            }
        }
    }

    override fun fillUiWithCurrentData() {
        //stub
    }

    companion object {

        @JvmStatic
        fun newInstance() = AddFoodInFridgeManuallyDialog()
    }
}