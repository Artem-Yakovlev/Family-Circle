package com.tydeya.familycircle.framework.editaccount.abstraction

import android.graphics.Bitmap
import com.tydeya.familycircle.ui.managerpart.editprofile.details.EditableFamilyMember

interface EditAccountTool {

    suspend fun editAccountData(phoneNumber: String, editableFamilyMember: EditableFamilyMember,
                                editableBitmap: Bitmap?)
}