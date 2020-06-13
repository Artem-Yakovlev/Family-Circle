package com.tydeya.familycircle.data.taskorganizer

import com.tydeya.familycircle.data.familymember.FamilyMember

data class TaskMember(
        val name: String,
        val phoneNumber: String,
        var isAdded: Boolean
) {
    constructor(
            familyMember: FamilyMember
    ) : this(
            familyMember.description.name,
            familyMember.fullPhoneNumber,
            false
    )
}