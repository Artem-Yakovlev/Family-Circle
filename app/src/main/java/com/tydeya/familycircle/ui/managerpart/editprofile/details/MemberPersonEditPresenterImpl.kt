package com.tydeya.familycircle.ui.managerpart.editprofile.details

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.framework.editaccount.details.EditAccountToolImpl
import com.tydeya.familycircle.ui.managerpart.editprofile.abstraction.MemberPersonEditPresenter
import com.tydeya.familycircle.ui.managerpart.editprofile.abstraction.MemberPersonEditView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MemberPersonEditPresenterImpl(val context: Context, val view: MemberPersonEditView) : MemberPersonEditPresenter {

    private val editAccountTool = EditAccountToolImpl(context)

    override fun checkDataForCorrect(editableFamilyMember: EditableFamilyMember): Boolean = editableFamilyMember.name != ""

    override fun editAccount(editableFamilyMember: EditableFamilyMember, editableBitmap: Bitmap?) {
        GlobalScope.launch(Dispatchers.IO) {
            editAccountTool.editAccountData(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
                    editableFamilyMember, editableBitmap)
        }
    }

}