package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import kotlinx.android.synthetic.main.dialog_food_for_buy_new_list.view.*
import javax.inject.Inject


class CreateBuyListDialog : DialogFragment() {

    @Inject
    lateinit var kitchenOrganizerInteractor: KitchenOrganizerInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_food_for_buy_new_list, null)

        builder.setView(view)

        view.dialog_shopping_list_create_button.setOnClickListener {

            var createFlag = true
            val title = view.dialog_shopping_list_name.text.toString().trim()

            if (title == "") {
                view.dialog_shopping_list_name.error = view.context!!
                        .resources.getString(R.string.empty_necessary_field_warning)
                createFlag = false
            } else {
                for (buyCatalog in kitchenOrganizerInteractor.buyCatalogs) {
                    if (buyCatalog.title.trim() == title) {
                        view.dialog_shopping_list_name.error = view.context!!
                                .resources.getString(R.string.dialog_new_buy_list_already_exist)
                        createFlag = false
                        break
                    }
                }
            }

            if (createFlag) {
                kitchenOrganizerInteractor.createBuyCatalog(title)
                dismiss()
            }
        }

        view.dialog_shopping_list_cancel_button.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }
}