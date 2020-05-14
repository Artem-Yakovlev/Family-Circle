package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogBuyListSettingsBinding
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.value
import com.tydeya.familycircle.viewmodel.AllBuyCatalogsViewModel
import com.tydeya.familycircle.viewmodel.BuyCatalogViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BuyCatalogSettingsDialog : DialogFragment() {

    private lateinit var allBuyCatalogsViewModel: AllBuyCatalogsViewModel
    private lateinit var buyCatalogViewModel: BuyCatalogViewModel

    private var _binding: DialogBuyListSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var root: View

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        root = requireActivity().layoutInflater.inflate(R.layout.dialog_buy_list_settings, null)

        return AlertDialog.Builder(activity).apply {
            setView(root)
        }.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogBuyListSettingsBinding.bind(root)

        allBuyCatalogsViewModel = ViewModelProvider(requireParentFragment())
                .get(AllBuyCatalogsViewModel::class.java)
        buyCatalogViewModel = ViewModelProviders.of(requireParentFragment())
                .get(BuyCatalogViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actualCatalogNameResource = allBuyCatalogsViewModel
                .getBuysCatalogTitleById(buyCatalogViewModel.catalogId)

        val actualCatalogName = if (actualCatalogNameResource is Resource.Success) {
            actualCatalogNameResource.data
        } else {
            dismiss()
            ""
        }

        binding.dialogShoppingListSettingsName.value = actualCatalogName

        binding.dialogBuyCatalogSettingsSaveButton.setOnClickListener {
            when (val title = binding.dialogShoppingListSettingsName.text.toString().trim()) {
                actualCatalogName -> {
                    dismiss()
                }
                "" -> {
                    binding.dialogShoppingListSettingsName.error = view.context!!.resources
                            .getString(R.string.empty_necessary_field_warning)
                }
                else -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        if (!allBuyCatalogsViewModel.isThereBuysCatalogWithName(title)) {
                            allBuyCatalogsViewModel.editCatalogName(buyCatalogViewModel.catalogId, title)
                            withContext(Dispatchers.Main) {
                                dismiss()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                binding.dialogShoppingListSettingsName.error = view.context!!
                                        .resources
                                        .getString(R.string.dialog_buy_list_settings_already_exist)
                            }
                        }
                    }
                }
            }
        }

        binding.dialogBuyListDeleteButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                allBuyCatalogsViewModel.deleteCatalog(buyCatalogViewModel.catalogId)
                withContext(Dispatchers.Main) {
                    dismiss()
                }
            }
        }

        binding.dialogBuyCatalogSettingsCancelButton.setOnClickListener { dismiss() }
    }

    companion object {

    }

}