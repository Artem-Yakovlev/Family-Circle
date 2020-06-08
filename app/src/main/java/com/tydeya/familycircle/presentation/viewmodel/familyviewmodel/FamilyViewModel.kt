package com.tydeya.familycircle.presentation.viewmodel.familyviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tydeya.familycircle.data.family.Family
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.domain.familyinteraction.FamilyInteractor
import com.tydeya.familycircle.domain.familyinteraction.FamilyNetworkInteractor
import com.tydeya.familycircle.domain.familyinteraction.FamilyNetworkInteractorCallback
import com.tydeya.familycircle.domain.familyselection.addFamilyMemberInFirestore
import com.tydeya.familycircle.presentation.viewmodel.base.FirestoreViewModel
import com.tydeya.familycircle.utils.Resource

class FamilyViewModel(
        familyId: String
) :
        FirestoreViewModel(), FamilyNetworkInteractorCallback {

    private val familyDataLiveData = MutableLiveData<Resource<Family>>(Resource.Loading())
    val familyData: LiveData<Resource<Family>> get() = familyDataLiveData

    private val familyMembersLiveData = MutableLiveData<Resource<ArrayList<FamilyMember>>>(Resource.Loading())
    val familyMembers: LiveData<Resource<ArrayList<FamilyMember>>> get() = familyMembersLiveData

    private val familyNetworkInteractor = FamilyNetworkInteractor(familyId, this)

    private var familyInteractor = FamilyInteractor(ArrayList())

    init {
        familyNetworkInteractor.register()
    }

    override fun familyDataUpdated(familyServerResource: Resource<Family>) {
        familyDataLiveData.value = familyServerResource
    }

    override fun familyMembersUpdated(membersServerResource: Resource<ArrayList<FamilyMember>>) {
        if (membersServerResource is Resource.Success) {
            familyInteractor.refreshData(membersServerResource.data)
        } else {
            familyInteractor.refreshData(ArrayList())
        }
        familyMembersLiveData.value = membersServerResource
    }

    override fun onCleared() {
        super.onCleared()
        familyNetworkInteractor.unregister()
    }

    fun getFamilyMemberByNumber(phoneNumber: String): Resource<FamilyMember> {
        return familyInteractor.familyMemberByPhone(phoneNumber)
    }

    fun inviteUserToFamily(familyId: String, phoneNumber: String) {
        addFamilyMemberInFirestore(familyId, phoneNumber)
    }
}