package com.tydeya.familycircle.presentation.ui.deliverypart.kitchenorganizer.pages.foodforbuy.alllists

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogFoodForBuyNewListBinding
import com.tydeya.familycircle.presentation.viewmodel.kitchen.AllBuyCatalogsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateBuyListDialog : DialogFragment() {

    private lateinit var allBuyCatalogsViewModel: AllBuyCatalogsViewModel

    private var _binding: DialogFoodForBuyNewListBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater.inflate(R.layout.dialog_food_for_buy_new_list, null)

        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogFoodForBuyNewListBinding.bind(root)
        allBuyCatalogsViewModel = ViewModelProvider(requireParentFragment())
                .get(AllBuyCatalogsViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dialogShoppingListCreateButton.setOnClickListener {
            val title = binding.dialogShoppingListName.text.toString().trim()

            if (title == "") {
                binding.dialogShoppingListName.error = view.context!!
                        .resources.getString(R.string.empty_necessary_field_warning)
            } else {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (!allBuyCatalogsViewModel.isThereBuysCatalogWithName(title)) {
                        allBuyCatalogsViewModel.createBuysCatalog(title)
                        withContext(Dispatchers.Main) {
                            dismiss()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            binding.dialogShoppingListName.error = view.context!!
                                    .resources.getString(R.string.dialog_new_buy_list_already_exist)
                        }
                    }
                }
            }
        }

        binding.dialogShoppingListCancelButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}