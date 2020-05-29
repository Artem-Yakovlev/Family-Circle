package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.family.SimpleFamily
import com.tydeya.familycircle.domain.familyselection.FamiliesNetworkListener
import com.tydeya.familycircle.domain.familyselection.FamiliesNetworkListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource

class FamilySelectionViewModel : ViewModel(), FamiliesNetworkListenerCallback {

    private val familiesNetworkListener: EventListenerObservable =
            FamiliesNetworkListener(this)

    private val familiesLiveData: MutableLiveData<Resource<List<SimpleFamily>>> =
            MutableLiveData(Resource.Loading())

    init {
        familiesNetworkListener.register()
    }

    override fun onCleared() {
        super.onCleared()
        familiesNetworkListener.unregister()
    }

    override fun familiesInformationUpdated(families: Resource<List<SimpleFamily>>) {
        this.familiesLiveData.value = families
    }

}