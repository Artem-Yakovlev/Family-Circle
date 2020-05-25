package com.tydeya.familycircle.presentation.ui.managerpart.editprofile.details

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerPresenter
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerUsable
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.presentation.ui.managerpart.editprofile.abstraction.MemberPersonEditPresenter
import com.tydeya.familycircle.presentation.ui.managerpart.editprofile.abstraction.MemberPersonEditView
import com.tydeya.familycircle.presentation.viewmodel.CroppedImageViewModel
import com.tydeya.familycircle.utils.KeyboardHelper
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.fragment_member_person_edit.*
import java.lang.ref.WeakReference
import java.util.*


class MemberPersonEditFragment : Fragment(), MemberPersonEditView, DatePickerUsable {

    private lateinit var presenter: MemberPersonEditPresenter
    private lateinit var familyInteractor: FamilyInteractor
    private lateinit var editableFamilyMember: EditableFamilyMember
    private var editableImageUri: Uri? = null
    private lateinit var croppedImageViewModel: CroppedImageViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        familyInteractor = App.getComponent().familyInteractor
        croppedImageViewModel = ViewModelProvider(requireActivity()).get(CroppedImageViewModel::class.java)
        return inflater.inflate(R.layout.fragment_member_person_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listenProfileImageEditable()

        editableFamilyMember = getEditableFamilyMemberByCurrentData()
        attachCurrentData(editableFamilyMember)

        presenter = MemberPersonEditPresenterImpl(requireContext(), this)
        edit_person_page_toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        edit_person_page_done.setOnClickListener {
            updateEditablePerson()
            if (presenter.checkDataForCorrect(editableFamilyMember)) {
                showAcceptAlertDialog()
            } else {
                edit_person_page_name_input.error =
                        requireContext().resources.getString(R.string.empty_necessary_field_warning)
            }
        }

        edit_person_datetime_picker.setOnClickListener(DatePickerPresenter(WeakReference(this),
                GregorianCalendar()))

        family_view_photo_edit.setOnClickListener {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1, 1)
                    .start(requireActivity())
        }
    }

    override fun dateChanged(selectedDateYear: Int, selectedDateMonth: Int, selectedDateDay: Int) {
        val calendar = GregorianCalendar(selectedDateYear, selectedDateMonth, selectedDateDay)
        editableFamilyMember.birthdate = calendar.timeInMillis
        edit_person_datetime_picker_output.text = DateRefactoring.getDateLocaleText(calendar)
        edit_person_datetime_picker_output.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.colorPrimary))
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
            edit_person_datetime_picker_output.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.colorPrimary))
        }

        edit_person_study.value = editableFamilyMember.studyPlace
        edit_person_work.value = editableFamilyMember.workPlace

        if (editableFamilyMember.imageAddress != "") {
            family_view_photo_edit.setPadding(0, 0, 0, 0)
            Glide.with(this)
                    .load(editableFamilyMember.imageAddress)
                    .into(family_view_photo_edit)
        } else {
            family_view_photo_edit.setPadding(20, 20, 20, 20)
        }

    }

    private fun updateEditablePerson() {
        with(editableFamilyMember) {
            name = edit_person_page_name_input.text.toString()
            studyPlace = edit_person_study.text.toString()
            workPlace = edit_person_work.text.toString()
        }
    }

    @SuppressLint("NewApi")
    private fun showAcceptAlertDialog() {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle(getString(R.string.person_edit_page_accept_alert_title))

        alertDialog.setPositiveButton(
                getString(R.string.yes_text)
        ) { _, _ ->

            var bitmap: Bitmap? = null

            editableImageUri?.let {
                bitmap = when {
                    Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media.getBitmap(
                            requireActivity().contentResolver, it
                    )
                    else -> ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(requireActivity().contentResolver, it)
                    )
                }
            }

            presenter.editAccount(editableFamilyMember, bitmap)

            NavHostFragment.findNavController(this@MemberPersonEditFragment).popBackStack()
            KeyboardHelper.hideKeyboard(activity)


        }
        alertDialog.setNegativeButton(
                getString(R.string.no_text)
        ) { _, _ -> run {} }

        alertDialog.show()
    }

    private fun listenProfileImageEditable() {
        croppedImageViewModel.croppedImage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    editableImageUri = it.data.uri
                    family_view_photo_edit.setPadding(0, 0, 0, 0)

                    Glide.with(this)
                            .load(editableImageUri)
                            .into(family_view_photo_edit)
                }
                is Resource.Loading -> {

                }
                is Resource.Failure -> {

                }
            }
        })
        croppedImageViewModel.imageProcessed()

    }


}
