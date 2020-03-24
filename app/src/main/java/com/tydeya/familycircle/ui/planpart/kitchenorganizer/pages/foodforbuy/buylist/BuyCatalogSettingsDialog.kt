package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.dialog_buy_list_settings.view.*
import javax.inject.Inject

class BuyCatalogSettingsDialog(private val catalogId: String,
                               val callback: BuyCatalogSettingsDialogCallback) : DialogFragment() {

    @Inject
    lateinit var kitchenOrganizerInteractor: KitchenOrganizerInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectCreateListDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val view = activity!!.layoutInflater
                .inflate(R.layout.dialog_buy_list_settings, null)


        view.dialog_shopping_list_settings_name.value =
                kitchenOrganizerInteractor.requireCatalogData(catalogId).title

        view.dialog_buy_catalog_settings_save_button.setOnClickListener {
            val title = view.dialog_shopping_list_settings_name.text.toString().trim()
            var canRenameFlag = true

            if (title == kitchenOrganizerInteractor.requireCatalogData(catalogId).title) {
                dismiss()
            } else if (title == "") {
                canRenameFlag = false
                view.dialog_shopping_list_settings_name.error = view.context!!
                        .resources.getString(R.string.empty_necessary_field_warning)
            } else {
                for (buyCatalog in kitchenOrganizerInteractor.buyCatalogs) {
                    if (buyCatalog.title == title) {
                        canRenameFlag = false
                        view.dialog_shopping_list_settings_name.error = view.context!!
                                .resources.getString(R.string.dialog_buy_list_settings_already_exist)
                        break
                    }
                }
            }

            if (canRenameFlag) {
                kitchenOrganizerInteractor.renameCatalog(catalogId, title)
                dismiss()
            }
        }

        view.dialog_buy_catalog_settings_cancel_button.setOnClickListener {
            dismiss()
        }

        view.dialog_buy_list_delete_button.setOnClickListener {
            callback.onDeleteCatalog()
            dismiss()
        }

        builder.setView(view)

        return builder.create()
    }

}

interface BuyCatalogSettingsDialogCallback {
    fun onDeleteCatalog()
}