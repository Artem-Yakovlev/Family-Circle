package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.dialog_edit_food_data.view.*
import javax.inject.Inject

class EditProductDataDialog(private val catalogId: String, private val actualTitle: String)
    :
        DialogFragment() {

    @Inject
    lateinit var kitchenOrganizerInteractor: KitchenOrganizerInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_edit_food_data, null)

        builder.setView(view)

        view.dialog_edit_food_create_button.setOnClickListener {
            var isCanCreateProduct = true
            val title = view.dialog_edit_food_name.text.toString().trim()

            if (title == actualTitle) {

                dismiss()

            } else if (title == "") {

                view.dialog_edit_food_name.error = view.context!!
                        .resources.getString(R.string.empty_necessary_field_warning)
                isCanCreateProduct = false

            } else {

//                for (food in kitchenOrganizerInteractor.requireCatalogData(catalogId).products) {
//                    if (food.title == title) {
//                        isCanCreateProduct = false
//                        view.dialog_edit_food_name.error = view.context!!
//                                .resources.getString(R.string.dialog_edit_food_data_already_exist)
//                        break
//                    }
//                }

            }

            if (isCanCreateProduct) {
                kitchenOrganizerInteractor.editProductInCatalog(catalogId, actualTitle, title)
                dismiss()
            }
        }

        view.dialog_edit_food_cancel_button.setOnClickListener {
            dismiss()
        }

        view.dialog_edit_food_name.value = actualTitle

        return builder.create()
    }
}