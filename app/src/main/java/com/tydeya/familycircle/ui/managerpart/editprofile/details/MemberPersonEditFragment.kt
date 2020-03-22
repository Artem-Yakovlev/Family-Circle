package com.tydeya.familycircle.ui.managerpart.editprofile.details

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerPresenter
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerUsable
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.ui.managerpart.editprofile.abstraction.MemberPersonEditPresenter
import com.tydeya.familycircle.ui.managerpart.editprofile.abstraction.MemberPersonEditView
import com.tydeya.familycircle.utils.KeyboardHelper
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.fragment_member_person_edit.*
import java.lang.ref.WeakReference
import java.util.*


class MemberPersonEditFragment : Fragment(), MemberPersonEditView, DatePickerUsable {

    private lateinit var presenter: MemberPersonEditPresenter
    private lateinit var familyInteractor: FamilyInteractor
    private lateinit var editableFamilyMember: EditableFamilyMember

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        familyInteractor = App.getComponent().familyInteractor
        return inflater.inflate(R.layout.fragment_member_person_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editableFamilyMember = getEditableFamilyMemberByCurrentData()
        attachCurrentData(editableFamilyMember)

        presenter = MemberPersonEditPresenterImpl(context!!, this)
        edit_person_page_toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        edit_person_page_done.setOnClickListener {
            updateEditablePerson()
            if (presenter.checkDataForCorrect(editableFamilyMember)) {
                showAcceptAlertDialog()
            } else {
                edit_person_page_name_input.error =
                        context!!.resources.getString(R.string.empty_necessary_field_warning)
            }
        }

        edit_person_datetime_picker.setOnClickListener(DatePickerPresenter(WeakReference(this),
                GregorianCalendar()))
    }

    override fun dateChanged(selectedDateYear: Int, selectedDateMonth: Int, selectedDateDay: Int) {

        val calendar = GregorianCalendar(selectedDateYear, selectedDateMonth, selectedDateDay)
        editableFamilyMember.birthdate = calendar.timeInMillis
        edit_person_datetime_picker_output.text = DateRefactoring.getDateLocaleText(calendar)
        edit_person_datetime_picker_output.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun getEditableFamilyMemberByCurrentData(): EditableFamilyMember {
        val userFamilyMember = FamilyAssistantImpl(familyInteractor.actualFamily)
                .getUserByPhone(FirebaseAuth.getInstance().currentUser!!.phoneNumber)

        return EditableFamilyMember(
                userFamilyMember.description.name ?: "",
                userFamilyMember.description.birthDate,
                userFamilyMember.careerData.studyPlace ?: "",
                userFamilyMember.careerData.workPlace ?: "")
    }

    private fun attachCurrentData(editableFamilyMember: EditableFamilyMember) {

        edit_person_page_name_input.value = editableFamilyMember.name

        if (editableFamilyMember.birthdate != -1L) {
            val calendar = GregorianCalendar()
            calendar.timeInMillis = editableFamilyMember.birthdate
            edit_person_datetime_picker_output.text = DateRefactoring.getDateLocaleText(calendar)
            edit_person_datetime_picker_output.setTextColor(resources.getColor(R.color.colorPrimary))
        }

        edit_person_study.value = editableFamilyMember.studyPlace
        edit_person_work.value = editableFamilyMember.workPlace
    }

    private fun updateEditablePerson() {
        with(editableFamilyMember) {
            name = edit_person_page_name_input.text.toString()
            studyPlace = edit_person_study.text.toString()
            workPlace = edit_person_work.text.toString()
        }
    }

    private fun showAcceptAlertDialog() {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle(context!!.resources.getString(R.string.person_edit_page_accept_alert_title))

        alertDialog.setPositiveButton(
                context!!.resources.getString(R.string.person_edit_page_accept_alert_positive_button)
        ) { _, _ ->
            run {
                presenter.editAccount(editableFamilyMember)
                NavHostFragment.findNavController(this).popBackStack()
                KeyboardHelper.hideKeyboard(activity)
            }
        }
        alertDialog.setNegativeButton(
                context!!.resources.getString(R.string.person_edit_page_accept_alert_negative_button)
        ) { _, _ -> run {} }

        alertDialog.show()
    }
}
