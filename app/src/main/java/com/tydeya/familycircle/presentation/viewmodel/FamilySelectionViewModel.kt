package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.domain.familyselection.*
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource

class FamilySelectionViewModel : ViewModel(), SelectableFamilyListenerCallback {

    private val selectableFamiliesListener: EventListenerObservable =
            SelectableFamiliesListener(this)

    private val familiesMutableLiveData: MutableLiveData<Resource<List<FamilyDTO>>> =
            MutableLiveData(Resource.Loading())

    val families: LiveData<Resource<List<FamilyDTO>>> get() = familiesMutableLiveData

    private val inviteCodesLiveData = MutableLiveData<Resource<ArrayList<String>>>(Resource.Loading())

    val inviteCodes: LiveData<Resource<ArrayList<String>>> = inviteCodesLiveData

    init {
        selectableFamiliesListener.register()
    }

    override fun selectableFamiliesUpdated(
            selectableFamilies: Resource<List<FamilyDTO>>,
            inviteCodes: Resource<ArrayList<String>>
    ) {
        familiesMutableLiveData.value = selectableFamilies
        inviteCodesLiveData.value = inviteCodes
    }

    override fun onCleared() {
        super.onCleared()
        selectableFamiliesListener.unregister()
    }

    fun createNewFamily(name: String) {
        createFamilyInFirestore(name)
    }

    fun joinToFamily(familyId: String) {
        acceptFamilyInvite(familyId)
    }

    fun refuseInvite(familyId: String) {
        refuseFamilyInvite(familyId)
    }
}