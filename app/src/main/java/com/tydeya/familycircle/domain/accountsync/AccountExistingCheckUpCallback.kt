package com.tydeya.familycircle.domain.accountsync

interface AccountExistingCheckUpCallback {

    fun accountIsNotExist()

    fun accountIsExist(userId: String, families: List<String>)
}