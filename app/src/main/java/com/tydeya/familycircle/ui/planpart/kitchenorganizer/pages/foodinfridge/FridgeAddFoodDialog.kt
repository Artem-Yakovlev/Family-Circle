package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import kotlinx.android.synthetic.main.dialog_fridge_add_food.view.*
import kotlinx.android.synthetic.main.dialog_new_food.view.*
import javax.inject.Inject

class FridgeAddFoodDialog : DialogFragment() {

    @Inject
    lateinit var kitchenOrganizerInteractor: KitchenOrganizerInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_fridge_add_food, null)

        view.dialog_fridge_add_food_create_button.setOnClickListener {
            val title = view.dialog_fridge_add_food_name.text.toString().trim()

            if (title == "") {
                view.dialog_new_food_name.error = view.context!!.resources
                        .getString(R.string.empty_necessary_field_warning)
            } else {
                kitchenOrganizerInteractor.addNewFoodInFridge(title)
                dismiss()
            }
        }

        view.dialog_fridge_add_food_cancel_button.setOnClickListener {
            dismiss()
        }

        builder.setView(view)

        return builder.create()
    }
}