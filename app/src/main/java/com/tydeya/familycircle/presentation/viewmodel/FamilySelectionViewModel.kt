package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.domain.familyselection.SelectableFamiliesListener
import com.tydeya.familycircle.domain.familyselection.SelectableFamilyListenerCallback
import com.tydeya.familycircle.domain.familyselection.createFamilyInFirebase
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource

class FamilySelectionViewModel : ViewModel(), SelectableFamilyListenerCallback {

    private val selectableFamiliesListener: EventListenerObservable =
            SelectableFamiliesListener(this)

    private val familiesMutableLiveData: MutableLiveData<Resource<List<FamilyDTO>>> =
            MutableLiveData(Resource.Loading())

    val familiesLiveData: LiveData<Resource<List<FamilyDTO>>> get() = familiesMutableLiveData

    init {
        selectableFamiliesListener.register()
    }

    override fun selectableFamiliesUpdated(
            selectableFamilies: Resource<List<FamilyDTO>>
    ) {
        familiesMutableLiveData.value = selectableFamilies
    }

    override fun onCleared() {
        super.onCleared()
        selectableFamiliesListener.unregister()
    }

    fun createNewFamily(name: String) {
        createFamilyInFirebase(name)
    }
}