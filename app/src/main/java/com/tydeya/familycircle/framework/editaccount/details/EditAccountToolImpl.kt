package com.tydeya.familycircle.framework.editaccount.details

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tydeya.familycircle.data.constants.Firebase
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.framework.editaccount.abstraction.EditAccountTool
import com.tydeya.familycircle.ui.managerpart.editprofile.details.EditableFamilyMember
import java.util.*

class EditAccountToolImpl(val context: Context) : EditAccountTool {

    override suspend fun editAccountData(phoneNumber: String, editableFamilyMember: EditableFamilyMember) {
        val firestore = FirebaseFirestore.getInstance()
        val taskQuerySnapshot = firestore.collection(FIRESTORE_USERS_COLLECTION)
                .whereEqualTo(FIRESTORE_USERS_PHONE_TAG, phoneNumber).get()
        taskQuerySnapshot.addOnCompleteListener { task: Task<QuerySnapshot> ->
            run {
                firestore
                        .collection(FIRESTORE_USERS_COLLECTION)
                        .document(task.result.documents[0].id)
                        .set(hashMapOf(
                                FIRESTORE_USERS_PHONE_TAG to phoneNumber,
                                FIRESTORE_USERS_NAME_TAG to editableFamilyMember.name,
                                FIRESTORE_USERS_BIRTHDATE_TAG to
                                        Date().apply { time = editableFamilyMember.birthdate },
                                FIRESTORE_USERS_STUDY_TAG to editableFamilyMember.studyPlace,
                                FIRESTORE_USERS_WORK_TAG to editableFamilyMember.workPlace)
                                as Map<String, Any>
                        )
            }
        }
    }
}