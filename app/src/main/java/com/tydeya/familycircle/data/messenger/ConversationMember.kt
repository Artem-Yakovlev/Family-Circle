package com.tydeya.familycircle.data.messenger

import com.tydeya.familycircle.data.familymember.FamilyMember

data class ConversationMember(val name: String, val phoneNumber: String, var isChecked: Boolean) {

    constructor(
            familyMember: FamilyMember
    ) : this(familyMember.description.name, familyMember.fullPhoneNumber, false)

}