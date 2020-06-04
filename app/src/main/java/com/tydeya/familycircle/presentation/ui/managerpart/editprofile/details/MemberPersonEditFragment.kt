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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.EditableFamilyMember
import com.tydeya.familycircle.databinding.FragmentMemberPersonEditBinding
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerPresenter
import com.tydeya.familycircle.framework.datepickerdialog.DatePickerUsable
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.framework.editaccount.details.EditAccountToolImpl
import com.tydeya.familycircle.presentation.viewmodel.CroppedImageViewModel
import com.tydeya.familycircle.presentation.viewmodel.familymemberedit.ChangeUserDataViewModel
import com.tydeya.familycircle.utils.KeyboardHelper
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MemberPersonEditFragment : Fragment(), DatePickerUsable {

    private lateinit var editableData: EditableFamilyMember
    private var editableImageUri: Uri? = null
    private lateinit var croppedImageViewModel: CroppedImageViewModel

    private lateinit var accountDataViewModel: ChangeUserDataViewModel

    private var _binding: FragmentMemberPersonEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        croppedImageViewModel = ViewModelProvider(requireActivity()).get(CroppedImageViewModel::class.java)
        accountDataViewModel = ViewModelProvider(this).get(ChangeUserDataViewModel::class.java)
        _binding = FragmentMemberPersonEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProfileImageEditableObserver()

        accountDataViewModel.editableFamilyMember.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> attachCurrentData(it.data)
                is Resource.Failure -> popBackStack()
            }
        })

        binding.editPersonPageToolbar.setNavigationOnClickListener {
            popBackStack()
        }

        binding.editPersonPageDone.setOnClickListener {
            if (binding.editPersonPageNameInput.isNotEmptyCheck(true)) {
                updateEditablePerson()
                showAcceptAlertDialog()
            }
        }

        binding.editPersonDatetimePicker.setOnClickListener(
                DatePickerPresenter(this, GregorianCalendar())
        )

        binding.familyViewPhotoEdit.setOnClickListener {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1, 1)
                    .start(requireActivity())
        }
    }

    override fun dateChanged(selectedDateYear: Int, selectedDateMonth: Int, selectedDateDay: Int) {
        val calendar = GregorianCalendar(selectedDateYear, selectedDateMonth, selectedDateDay)
        editableData.birthdate = calendar.timeInMillis

        binding.editPersonDatetimePickerOutput.text = DateRefactoring.getDateLocaleText(calendar)
        binding.editPersonDatetimePickerOutput.setTextColor(ContextCompat.getColor(requireContext(),
                R.color.colorPrimary))
    }

    private fun attachCurrentData(editableFamilyMember: EditableFamilyMember) {
        editableData = editableFamilyMember.copy()
        binding.editPersonPageNameInput.value = editableFamilyMember.name

        if (editableFamilyMember.birthdate != -1L) {
            val calendar = GregorianCalendar()
            calendar.timeInMillis = editableFamilyMember.birthdate
            binding.editPersonDatetimePickerOutput.text = DateRefactoring.getDateLocaleText(calendar)
            binding.editPersonDatetimePickerOutput.setTextColor(ContextCompat.getColor(requireContext(),
                    R.color.colorPrimary))
        }

        binding.editPersonStudy.value = editableFamilyMember.studyPlace
        binding.editPersonWork.value = editableFamilyMember.workPlace

        if (editableFamilyMember.imageAddress != "") {
            binding.familyViewPhotoEdit.setPadding(0, 0, 0, 0)
            Glide.with(this)
                    .load(editableFamilyMember.imageAddress)
                    .into(binding.familyViewPhotoEdit)
        } else {
            binding.familyViewPhotoEdit.setPadding(20, 20, 20, 20)
        }
    }

    private fun updateEditablePerson() {
        with(editableData) {
            name = binding.editPersonPageNameInput.text.toString()
            studyPlace = binding.editPersonStudy.text.toString()
            workPlace = binding.editPersonWork.text.toString()
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
                    Build.VERSION.SDK_INT < 28 -> MediaStore.Images.Media
                            .getBitmap(requireActivity().contentResolver, it)
                    else -> ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(requireActivity().contentResolver, it)
                    )
                }
            }

            GlobalScope.launch(Dispatchers.Default) {
                EditAccountToolImpl(requireContext())
                        .editAccountData(getUserPhone(), editableData, bitmap)
            }

            popBackStack()
            requireContext().showToast(R.string.success)
            KeyboardHelper.hideKeyboard(requireActivity())

        }
        alertDialog.setNegativeButton(
                getString(R.string.no_text)
        ) { _, _ -> run {} }

        alertDialog.show()
    }

    private fun initProfileImageEditableObserver() {
        croppedImageViewModel.croppedImage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is Resource.Success -> {
                    editableImageUri = it.data.uri
                    binding.familyViewPhotoEdit.setPadding(0, 0, 0, 0)

                    Glide.with(this)
                            .load(editableImageUri)
                            .into(binding.familyViewPhotoEdit)
                }
            }
        })
        croppedImageViewModel.imageProcessed()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
