package com.tydeya.familycircle.utils.extensions

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Application
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_COLLECTION

var Context.currentFamilyId
    set(familyId) {
        getSharedPreferences(Application.SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
                .edit().putString(Application.CURRENT_FAMILY_ID, familyId).apply()
    }
    get() = getSharedPreferences(Application.SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
            .getString(Application.CURRENT_FAMILY_ID, "") ?: ""

fun getUserPhone() = FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""

fun firestoreFamily(familyId: String) = FirebaseFirestore.getInstance()
        .collection(FAMILY_COLLECTION)
        .document(familyId)