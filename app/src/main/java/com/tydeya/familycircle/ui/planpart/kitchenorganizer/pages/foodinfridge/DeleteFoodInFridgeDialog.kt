package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogDeleteFoodInFridgeBinding
import com.tydeya.familycircle.viewmodel.FoodInFridgeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteFoodInFridgeDialog private constructor() : DialogFragment() {

    private lateinit var productId: String

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    private var _binding: DialogDeleteFoodInFridgeBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater.inflate(R.layout.dialog_delete_food_in_fridge, null)

        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogDeleteFoodInFridgeBinding.bind(root)
        foodInFridgeViewModel = ViewModelProviders.of(requireParentFragment())
                .get(FoodInFridgeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productId = requireArguments().getString(PRODUCT_ID, "")

        binding.dialogDeleteFoodFridgeEatenFoodButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                foodInFridgeViewModel.deleteFromFridgeEatenFood(productId)
                withContext(Dispatchers.Main) {
                    dismiss()
                }
            }
        }
        binding.dialogDeleteFoodFridgeBadFoodButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                foodInFridgeViewModel.deleteFromFridgeBadFood(productId)
                withContext(Dispatchers.Main) {
                    dismiss()
                }
            }
        }

        binding.dialogDeleteFoodFridgeCancelButton.setOnClickListener {
            dismiss()
        }
    }

    companion object {

        private const val PRODUCT_ID = "product_id"

        @JvmStatic
        fun newInstance(actualTitle: String) = DeleteFoodInFridgeDialog().apply {
            arguments = Bundle().apply {
                putString(PRODUCT_ID, actualTitle)
            }
        }
    }

}