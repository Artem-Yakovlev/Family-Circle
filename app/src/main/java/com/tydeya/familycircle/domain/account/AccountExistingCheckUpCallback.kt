package com.tydeya.familycircle.domain.account

interface AccountExistingCheckUpCallback {

    fun accountIsNotExist()

    fun accountIsExist(userId: String, families: List<String>)
}