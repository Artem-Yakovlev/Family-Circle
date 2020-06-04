package com.tydeya.familycircle.presentation.viewmodel.familymemberedit

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.familymember.EditableFamilyMember
import com.tydeya.familycircle.domain.account.AccountDataEventListener
import com.tydeya.familycircle.domain.account.AccountDataEventListenerCallback
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.getUserPhone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ChangeUserDataViewModel : ViewModel(), AccountDataEventListenerCallback {

    private val accountDataListener = AccountDataEventListener(getUserPhone(), this)

    private val accountData = MutableLiveData<Resource<EditableFamilyMember>>(Resource.Loading())
    val editableFamilyMember: LiveData<Resource<EditableFamilyMember>> = accountData

    init {
        accountDataListener.register()
    }

    override fun accountDataUpdated(familyMember: Resource<EditableFamilyMember>) {
        if (familyMember is Resource.Success) {
            if (accountData.value is Resource.Loading) {
                accountData.value = familyMember
            }
        } else {
            accountData.value = familyMember
        }
    }

    override fun onCleared() {
        super.onCleared()
        accountDataListener.unregister()
    }

}