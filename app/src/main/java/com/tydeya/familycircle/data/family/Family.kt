package com.tydeya.familycircle.data.family

import com.tydeya.familycircle.data.familymember.FamilyMember
import java.util.*

class Family(val id: Long, var title: String, var familyMembers: ArrayList<FamilyMember>) {

    fun getFamilyMemberExceptUserPhone(userPhone: String): ArrayList<FamilyMember> {
        val familyMemberExceptUser = ArrayList<FamilyMember>()
        for (familyMember in familyMembers) {
            if (familyMember.fullPhoneNumber != userPhone) {
                familyMemberExceptUser.add(familyMember)
            }
        }
        return familyMemberExceptUser
    }

}