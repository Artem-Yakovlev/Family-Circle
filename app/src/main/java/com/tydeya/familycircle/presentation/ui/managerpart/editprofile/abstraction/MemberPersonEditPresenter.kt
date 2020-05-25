package com.tydeya.familycircle.presentation.ui.managerpart.editprofile.abstraction

import android.graphics.Bitmap
import com.tydeya.familycircle.presentation.ui.managerpart.editprofile.details.EditableFamilyMember

interface MemberPersonEditPresenter {

    fun checkDataForCorrect(editableFamilyMember: EditableFamilyMember): Boolean

    fun editAccount(editableFamilyMember: EditableFamilyMember, editableBitmap: Bitmap?)
}