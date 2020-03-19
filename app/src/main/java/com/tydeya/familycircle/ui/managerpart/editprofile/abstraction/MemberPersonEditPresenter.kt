package com.tydeya.familycircle.ui.managerpart.editprofile.abstraction

import com.tydeya.familycircle.ui.managerpart.editprofile.details.EditableFamilyMember

interface MemberPersonEditPresenter {

    fun checkDataForCorrect(editableFamilyMember: EditableFamilyMember): Boolean

    fun editAccount(editableFamilyMember: EditableFamilyMember)
}