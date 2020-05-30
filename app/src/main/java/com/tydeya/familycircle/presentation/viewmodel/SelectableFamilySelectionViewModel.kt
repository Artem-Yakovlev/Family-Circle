package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.domain.familyselection.SelectableFamiliesListener
import com.tydeya.familycircle.domain.familyselection.SelectableFamilyListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource

class SelectableFamilySelectionViewModel : ViewModel(), SelectableFamilyListenerCallback {

    private val selectableFamiliesListener: EventListenerObservable =
            SelectableFamiliesListener(this)

    val familiesLiveData: MutableLiveData<Resource<List<FamilyDTO>>> =
            MutableLiveData(Resource.Loading())

    init {
        selectableFamiliesListener.register()
    }

    override fun onCleared() {
        super.onCleared()
        selectableFamiliesListener.unregister()
    }

    override fun selectableFamiliesUpdated(selectableFamilies: Resource<List<FamilyDTO>>) {
        familiesLiveData.value = selectableFamilies
    }
}