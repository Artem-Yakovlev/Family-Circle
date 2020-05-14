package com.tydeya.familycircle.ui.planpart.kitchenorganizer.barcodescanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.kitchenorganizer.barcodescanner.ScannedProduct
import com.tydeya.familycircle.databinding.DialogKitchenOrganizerFoodActionBinding
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.FoodActionDialog
import com.tydeya.familycircle.utils.value
import com.tydeya.familycircle.viewmodel.BarcodeScannerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateProductFromBarcodeDialog : FoodActionDialog() {

    private lateinit var barcodeScannerViewModel: BarcodeScannerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogKitchenOrganizerFoodActionBinding.bind(root)
        barcodeScannerViewModel = ViewModelProviders.of(requireActivity())
                .get(BarcodeScannerViewModel::class.java)

        return binding.root
    }

    override fun fillUiWithCurrentData() {
        super.fillUiWithCurrentData()
        binding.productNameInput.value = requireArguments().getString(FOOD_NAME, "")

    }

    override fun action() {
        val title = binding.productNameInput.text.toString().trim()

        if (title == "") {
            binding.productNameInput.error = requireContext().resources
                    .getString(R.string.empty_necessary_field_warning)
        } else {
            GlobalScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.Main) {
                    barcodeScannerViewModel.addProductToFridge(createFoodByInputtedData())
                    Toast.makeText(requireContext(), resources
                            .getString(R.string.barcode_scanner_activity_product_added),
                            Toast.LENGTH_LONG).show()
                    dismiss()
                }
            }
        }
    }

    companion object {
        private const val FOOD_NAME = "name"

        @JvmStatic
        fun newInstance(scannedProduct: ScannedProduct): CreateProductFromBarcodeDialog {
            return CreateProductFromBarcodeDialog().apply {
                arguments = Bundle().apply {
                    putString(FOOD_NAME, scannedProduct.name)
                }
            }
        }
    }
}