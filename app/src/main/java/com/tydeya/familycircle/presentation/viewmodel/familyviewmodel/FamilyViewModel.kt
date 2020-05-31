package com.tydeya.familycircle.presentation.viewmodel.familyviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.family.Family
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.domain.familyinteraction.FamilyNetworkInteractor
import com.tydeya.familycircle.domain.familyinteraction.FamilyNetworkInteractorCallback
import com.tydeya.familycircle.utils.Resource

class FamilyViewModel(
        private val familyId: String
) :
        ViewModel(), FamilyNetworkInteractorCallback {

    private val familyDataLiveData = MutableLiveData<Resource<Family>>(Resource.Loading())
    val familyData: LiveData<Resource<Family>> get() = familyDataLiveData

    private val familyMembersLiveData = MutableLiveData<Resource<ArrayList<FamilyMember>>>(Resource.Loading())
    val familyMembers: LiveData<Resource<ArrayList<FamilyMember>>> get() = familyMembersLiveData

    private val familyNetworkInteractor = FamilyNetworkInteractor(familyId, this)

    init {
        familyNetworkInteractor.register()
    }

    override fun familyDataUpdated(family: Resource<Family>) {
        familyDataLiveData.value = family
    }

    override fun familyMembersUpdated(familyMembers: Resource<ArrayList<FamilyMember>>) {
        familyMembersLiveData.value = familyMembers
    }

    override fun onCleared() {
        super.onCleared()
        familyNetworkInteractor.unregister()
    }
}