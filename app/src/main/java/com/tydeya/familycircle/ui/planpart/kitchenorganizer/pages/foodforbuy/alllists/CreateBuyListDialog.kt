package com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.databinding.DialogFoodForBuyNewListBinding
import com.tydeya.familycircle.databinding.FragmentFoodForBuyBinding
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import com.tydeya.familycircle.viewmodel.AllBuyCatalogsViewModel
import kotlinx.android.synthetic.main.dialog_food_for_buy_new_list.view.*
import javax.inject.Inject


class CreateBuyListDialog : DialogFragment() {

    private var _binding: DialogFoodForBuyNewListBinding? = null
    private var allBuyCatalogsViewModel: AllBuyCatalogsViewModel? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectDialog(this)
        Log.d("ASMR", "onCreate")
        allBuyCatalogsViewModel = ViewModelProvider(requireParentFragment())
                .get(AllBuyCatalogsViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("ASMR", "onCreateDialog")
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_food_for_buy_new_list, null)

        //binding = DialogFoodForBuyNewListBinding.inflate(inflater, view, false)


        builder.setView(view)

        view.dialog_shopping_list_create_button.setOnClickListener {

            val createFlag: Boolean
            val title = view.dialog_shopping_list_name.text.toString().trim()

            if (title == "") {
                view.dialog_shopping_list_name.error = view.context!!
                        .resources.getString(R.string.empty_necessary_field_warning)
                createFlag = false
            } else {
                createFlag = allBuyCatalogsViewModel?.ifPossibleThenCreateBuysCatalog(title)
                        ?: false
            }

            if (createFlag) {
                dismiss()
            } else {
                view.dialog_shopping_list_name.error = view.context!!
                        .resources.getString(R.string.dialog_new_buy_list_already_exist)
            }
        }

        view.dialog_shopping_list_cancel_button.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("ASMR", "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() {

        }
    }

}