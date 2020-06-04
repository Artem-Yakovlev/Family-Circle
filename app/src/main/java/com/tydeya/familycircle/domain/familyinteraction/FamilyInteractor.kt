package com.tydeya.familycircle.domain.familyinteraction

import android.util.Log
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.utils.Resource

class FamilyInteractor(familyMembers: ArrayList<FamilyMember>) {

    private val membersMap = HashMap<String, FamilyMember>()

    init {
        familyMembers.forEach { membersMap[it.fullPhoneNumber] = it }
    }

    fun familyMemberByPhone(phoneNumber: String): Resource<FamilyMember> {
        return if (membersMap.containsKey(phoneNumber)) {
            Resource.Success(membersMap[phoneNumber]!!)
        } else {
            Resource.Failure(IllegalStateException("Family member is not found"))
        }
    }

    fun refreshData(members: ArrayList<FamilyMember>) {
        membersMap.clear()
        members.forEach { membersMap[it.fullPhoneNumber] = it }
    }
}