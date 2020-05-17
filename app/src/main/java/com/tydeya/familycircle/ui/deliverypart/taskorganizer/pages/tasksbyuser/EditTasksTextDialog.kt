package com.tydeya.familycircle.ui.deliverypart.taskorganizer.pages.tasksbyuser

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.taskorganizer.FamilyTask
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.dialog_tasks_edit.view.*
import javax.inject.Inject

class EditTasksTextDialog(val familyTask: FamilyTask) : DialogFragment() {

    @Inject
    lateinit var tasksOrganizerInteractor: TasksOrganizerInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.getComponent().injectDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_tasks_edit, null)

        view.your_errand_edit_text_dialog_input.value = familyTask.text

        view.your_errand_edit_text_dialog_save_button.setOnClickListener {
            if (!DataConfirming.isEmptyNecessaryCheck(view.your_errand_edit_text_dialog_input, true)) {
                tasksOrganizerInteractor.editTaskText(familyTask,
                        view.your_errand_edit_text_dialog_input.text.toString().trim())
                dismiss()
            }
        }

        view.your_errand_edit_text_dialog_cancel_button.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }
}