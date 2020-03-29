package com.tydeya.familycircle.ui.managerpart.editprofile.abstraction

import android.graphics.Bitmap
import android.net.Uri
import com.tydeya.familycircle.ui.managerpart.editprofile.details.EditableFamilyMember

interface MemberPersonEditPresenter {

    fun checkDataForCorrect(editableFamilyMember: EditableFamilyMember): Boolean

    fun editAccount(editableFamilyMember: EditableFamilyMember, editableBitmap: Bitmap?)
}