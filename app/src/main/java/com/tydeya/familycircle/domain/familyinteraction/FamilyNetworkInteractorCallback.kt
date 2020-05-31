package com.tydeya.familycircle.domain.familyinteraction

import com.tydeya.familycircle.data.family.Family
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.utils.Resource

interface FamilyNetworkInteractorCallback {

    fun familyDataUpdated(family: Resource<Family>)

    fun familyMembersUpdated(familyMembers: Resource<ArrayList<FamilyMember>>)
}