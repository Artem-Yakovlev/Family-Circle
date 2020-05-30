package com.tydeya.familycircle.data.authentication.accountsync

import com.google.firebase.firestore.QuerySnapshot

interface AccountExistingCheckUpCallback {

    fun accountIsNotExist()

    fun accountIsExist(userId: String, families: List<String>)
}