package com.tydeya.familycircle.ui.managerpart.editprofile.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerPresenter
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerUsable
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.ui.managerpart.editprofile.abstraction.MemberPersonEditPresenter
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.fragment_member_person_edit.*
import java.lang.ref.WeakReference
import java.util.*


class MemberPersonEditFragment : Fragment(), DatePickerUsable {

    private lateinit var presenter: MemberPersonEditPresenter
    private lateinit var familyInteractor: FamilyInteractor


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_member_person_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        familyInteractor = App.getComponent().familyInteractor
        bindOldData()

        val datePickerPresenter = DatePickerPresenter(WeakReference(this), GregorianCalendar())
        edit_person_datetime_picker.setOnClickListener(datePickerPresenter)
    }

    override fun dateChanged(selectedDateYear: Int, selectedDateMonth: Int, selectedDateDay: Int) {

        val calendar = GregorianCalendar(selectedDateYear, selectedDateMonth, selectedDateDay)

        //presenter.birthDateChanged(calendar.getTimeInMillis());
        edit_person_datetime_picker_output.text = DateRefactoring.getDateLocaleText(calendar)
        edit_person_datetime_picker_output.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private fun bindOldData() {
        val phoneNumber = FirebaseAuth.getInstance().currentUser!!.phoneNumber
        val userFamilyMember = FamilyAssistantImpl(familyInteractor.actualFamily).getUserByPhone(phoneNumber)

        edit_person_page_name_input.value = userFamilyMember.description.name
                ?: ""

        if (userFamilyMember.description.birthDate != -1L) {
            val calendar = GregorianCalendar()
            calendar.timeInMillis = userFamilyMember.description.birthDate
            edit_person_datetime_picker_output.text = DateRefactoring.getDateLocaleText(calendar)
        }

        edit_person_study.value = userFamilyMember.careerData.studyPlace ?: ""
        edit_person_work.value = userFamilyMember.careerData.workPlace ?: ""
    }

}
