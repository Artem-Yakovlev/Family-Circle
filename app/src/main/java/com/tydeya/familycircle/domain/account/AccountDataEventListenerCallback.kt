package com.tydeya.familycircle.domain.account

import com.tydeya.familycircle.data.familymember.EditableFamilyMember
import com.tydeya.familycircle.utils.Resource

interface AccountDataEventListenerCallback {

    fun accountDataUpdated(familyMember: Resource<EditableFamilyMember>)

}