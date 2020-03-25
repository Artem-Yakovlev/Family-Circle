package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import kotlinx.android.synthetic.main.dialog_delete_food_in_fridge.view.*
import javax.inject.Inject

class DeleteFoodInFridgeDialog(val title: String) : DialogFragment() {

    @Inject
    lateinit var kitchenOrganizerInteractor: KitchenOrganizerInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_delete_food_in_fridge, null)

        view.dialog_delete_food_fridge_eaten_food_button.setOnClickListener {
            kitchenOrganizerInteractor.deleteFromFridgeEatenFood(title)
            dismiss()
        }

        view.dialog_delete_food_fridge_bad_food_button.setOnClickListener {
            kitchenOrganizerInteractor.deleteFromFridgeBadFood(title)
            dismiss()
        }

        view.dialog_delete_food_fridge_cancel_button.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }

}