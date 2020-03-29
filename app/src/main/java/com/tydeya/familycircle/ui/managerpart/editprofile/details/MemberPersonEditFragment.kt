package com.tydeya.familycircle.ui.managerpart.editprofile.details

import android.app.AlertDialog
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerPresenter
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerUsable
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.framework.datepickerdialog.ImageCropperUsable
import com.tydeya.familycircle.ui.managerpart.editprofile.abstraction.MemberPersonEditPresenter
import com.tydeya.familycircle.ui.managerpart.editprofile.abstraction.MemberPersonEditView
import com.tydeya.familycircle.utils.KeyboardHelper
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.fragment_member_person_edit.*
import java.io.ByteArrayOutputStream
import java.lang.ref.WeakReference
import java.util.*


class MemberPersonEditFragment : Fragment(), MemberPersonEditView, DatePickerUsable, ImageCropperUsable {

    private lateinit var presenter: MemberPersonEditPresenter
    private lateinit var familyInteractor: FamilyInteractor
    private lateinit var editableFamilyMember: EditableFamilyMember
    private var editableImageUri: Uri? = null

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

        family_view_photo_edit.setOnClickListener {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1, 1)
                    .start(activity!!)
        }
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
                userFamilyMember.description.imageAddress,
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

        if (editableFamilyMember.imageAddress != "") {
            family_view_photo_edit.setPadding(0,0,0,0)
            Glide.with(this)
                    .load(editableFamilyMember.imageAddress)
                    .into(family_view_photo_edit)
        } else {
            family_view_photo_edit.setPadding(20,20,20,20)
        }


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
                presenter.editAccount(editableFamilyMember,
                        MediaStore.Images.Media.getBitmap(activity!!.contentResolver, editableImageUri))
                NavHostFragment.findNavController(this).popBackStack()
                KeyboardHelper.hideKeyboard(activity)
            }
        }
        alertDialog.setNegativeButton(
                context!!.resources.getString(R.string.person_edit_page_accept_alert_negative_button)
        ) { _, _ -> run {} }

        alertDialog.show()
    }

    override fun imageCroppedSuccessfully(activityResult: CropImage.ActivityResult?) {
        editableImageUri = activityResult!!.uri
        family_view_photo_edit.setPadding(0, 0, 0, 0)

        Glide.with(this)
                .load(editableImageUri)
                .into(family_view_photo_edit)
    }

    override fun imageCroppedWithError(activityResult: CropImage.ActivityResult?) {
        //TODO stub
    }
}
