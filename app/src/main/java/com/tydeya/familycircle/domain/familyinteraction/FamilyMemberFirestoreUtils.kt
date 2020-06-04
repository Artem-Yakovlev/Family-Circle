package com.tydeya.familycircle.domain.familyinteraction

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_BIRTH_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_IMAGE_PATH
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_LAST_ONLINE
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_NAME_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_STUDY_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_WORK_TAG
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.familymember.FamilyMemberCareerData
import com.tydeya.familycircle.data.familymember.FamilyMemberDescription
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.utils.extensions.getUserPhone
import com.tydeya.familycircle.utils.extensions.ifNullToEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

private const val ONLINE_INTERVAL = 120000

fun convertFirestoreDataToFamilyMember(snapshot: DocumentSnapshot): FamilyMember {
    val description = FamilyMemberDescription(
            name = snapshot.getString(FIRESTORE_USERS_NAME_TAG).ifNullToEmpty(),
            birthDate = DateRefactoring
                    .dateToTimestamp(snapshot.getDate(FIRESTORE_USERS_BIRTH_TAG)),
            imageAddress = snapshot.getString(FIRESTORE_USERS_IMAGE_PATH).ifNullToEmpty()
    )
    val careerData = FamilyMemberCareerData(
            studyPlace = snapshot.getString(FIRESTORE_USERS_STUDY_TAG).ifNullToEmpty(),
            workPlace = snapshot.getString(FIRESTORE_USERS_WORK_TAG).ifNullToEmpty()
    )
    val lastInteractionTime = snapshot.getDate(FIRESTORE_USERS_LAST_ONLINE) ?: Date(100)

    return FamilyMember(
            fullPhoneNumber = snapshot.id,
            description = description,
            careerData = careerData,
            isOnline = Date().time - lastInteractionTime.time < ONLINE_INTERVAL
    )
}

fun serverInteractionDetect() {
    GlobalScope.launch(Dispatchers.Default) {
        FirebaseFirestore.getInstance()
                .collection(FIRESTORE_USERS_COLLECTION)
                .document(getUserPhone())
                .update(mapOf(FIRESTORE_USERS_LAST_ONLINE to Date()))
    }
}

