package com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.data.taskorganizer.FamilyTaskStatus
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import kotlinx.android.synthetic.main.dialog_tasks_create.view.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class CreateTaskDialog : DialogFragment() {

    @Inject
    lateinit var tasksOrganizerInteractor: TasksOrganizerInteractor

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    private val names = ArrayList<String>()
    private val phoneNumbers = ArrayList<String>()

    init {
        App.getComponent().injectDialog(this)
        familyInteractor.actualFamily.familyMembers.forEach {
            if (it.fullPhoneNumber != FirebaseAuth.getInstance().currentUser!!.phoneNumber) {
                names.add(it.description.name)
                phoneNumbers.add(it.fullPhoneNumber)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_tasks_create, null)

        setSpinnerData(view)

        view.create_task_dialog_create_button.setOnClickListener {
            if (!DataConfirming.isEmptyNecessaryCheck(view.create_task_dialog_main_text, true)) {
                tasksOrganizerInteractor.createTask(
                        FamilyTask("",
                                FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                                phoneNumbers[view.create_task_dialog_spinner.selectedItemPosition],
                                view.create_task_dialog_main_text.text.toString(),
                                Date().time,
                                FamilyTaskStatus.AWAITING_COMPLETION
                        ))
                dismiss()
            }
        }

        view.create_task_dialog_cancel_button.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()

    }

    private fun setSpinnerData(view: View) {
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(context!!,
                android.R.layout.simple_spinner_dropdown_item, names)

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        view.create_task_dialog_spinner.adapter = spinnerArrayAdapter
    }
}