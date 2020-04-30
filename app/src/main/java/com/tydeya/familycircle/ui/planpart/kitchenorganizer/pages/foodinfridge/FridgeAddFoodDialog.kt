package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogDeleteFoodInFridgeBinding
import com.tydeya.familycircle.databinding.DialogFridgeAddFoodBinding
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.viewmodel.FoodInFridgeViewModel
import kotlinx.android.synthetic.main.dialog_fridge_add_food.view.*
import kotlinx.android.synthetic.main.dialog_new_food.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class FridgeAddFoodDialog : DialogFragment() {

    private lateinit var foodInFridgeViewModel: FoodInFridgeViewModel

    private var _binding: DialogFridgeAddFoodBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater.inflate(R.layout.dialog_fridge_add_food, null)

        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = DialogFridgeAddFoodBinding.bind(root)
        foodInFridgeViewModel = ViewModelProviders.of(requireParentFragment())
                .get(FoodInFridgeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogFridgeAddFoodCreateButton.setOnClickListener {
            val title = view.dialog_fridge_add_food_name.text.toString().trim()

            if (title == "") {
                binding.dialogFridgeAddFoodName.error = view.context!!.resources
                        .getString(R.string.empty_necessary_field_warning)
            } else {
                GlobalScope.launch(Dispatchers.IO) {
                    foodInFridgeViewModel.addNewFoodInFridge(title)
                }
                dismiss()
            }
        }
        binding.dialogFridgeAddFoodCancelButton.setOnClickListener {
            dismiss()
        }
    }
}