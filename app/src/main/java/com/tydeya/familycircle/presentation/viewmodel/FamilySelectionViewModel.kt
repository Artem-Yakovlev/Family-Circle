package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.domain.familyselection.SelectableFamiliesListener
import com.tydeya.familycircle.domain.familyselection.SelectableFamilyListenerCallback
import com.tydeya.familycircle.domain.familyselection.createFamilyInFirebase
import com.tydeya.familycircle.domain.familyselection.selectCurrentFamilyInFirebase
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FamilySelectionViewModel : ViewModel(), SelectableFamilyListenerCallback {

    private val selectableFamiliesListener: EventListenerObservable =
            SelectableFamiliesListener(this)

    private val familiesMutableLiveData: MutableLiveData<Resource<List<FamilyDTO>>> =
            MutableLiveData(Resource.Loading())

    val familiesLiveData: LiveData<Resource<List<FamilyDTO>>> get() = familiesMutableLiveData


    private val currentFamilyIdMutableLiveData: MutableLiveData<Resource<String>> =
            MutableLiveData(Resource.Success(""))

    val currentFamilyIdLiveData: LiveData<Resource<String>> = currentFamilyIdMutableLiveData

    init {
        selectableFamiliesListener.register()
    }

    override fun selectableFamiliesUpdated(
            selectableFamilies: Resource<List<FamilyDTO>>,
            currentFamilyId: Resource<String>
    ) {
        familiesMutableLiveData.value = selectableFamilies
        currentFamilyIdMutableLiveData.value = currentFamilyId
    }

    override fun onCleared() {
        super.onCleared()
        selectableFamiliesListener.unregister()
    }

    fun createNewFamily(name: String) {
        createFamilyInFirebase(name)
    }

    fun selectFamily(familyId: String) {
        currentFamilyIdMutableLiveData.value = Resource.Loading()
        GlobalScope.launch {
            selectCurrentFamilyInFirebase(familyId)
        }
    }
}