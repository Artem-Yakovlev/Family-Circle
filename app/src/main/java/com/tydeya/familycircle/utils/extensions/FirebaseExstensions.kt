package com.tydeya.familycircle.utils.extensions

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.data.constants.Application

var Activity.currentFamilyId
    set(familyId) {
        getSharedPreferences(Application.SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
                .edit().putString(Application.CURRENT_FAMILY_ID, familyId).apply()
    }
    get() = getSharedPreferences(Application.SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
            .getString(Application.CURRENT_FAMILY_ID, "") ?: ""

fun getUserPhone() = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""