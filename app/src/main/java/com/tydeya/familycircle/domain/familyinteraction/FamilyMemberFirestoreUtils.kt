package com.tydeya.familycircle.domain.familyinteraction

import com.google.firebase.firestore.DocumentSnapshot
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_BIRTHDATE_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_IMAGE_ADDRESS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_NAME_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_STUDY_TAG
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_WORK_TAG
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.familymember.FamilyMemberCareerData
import com.tydeya.familycircle.data.familymember.FamilyMemberDescription
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import com.tydeya.familycircle.utils.ifNullToEmpty

fun convertFirestoreDataToFamilyMember(documentSnapshot: DocumentSnapshot): FamilyMember {

    val description = FamilyMemberDescription(
            name = documentSnapshot.getString(FIRESTORE_USERS_NAME_TAG).ifNullToEmpty(),
            birthDate = DateRefactoring
                    .dateToTimestamp(documentSnapshot.getDate(FIRESTORE_USERS_BIRTHDATE_TAG)),
            imageAddress = documentSnapshot.getString(FIRESTORE_USERS_IMAGE_ADDRESS).ifNullToEmpty()
    )

    val careerData = FamilyMemberCareerData(
            studyPlace = documentSnapshot.getString(FIRESTORE_USERS_STUDY_TAG).ifNullToEmpty(),
            workPlace = documentSnapshot.getString(FIRESTORE_USERS_WORK_TAG).ifNullToEmpty())

    return FamilyMember(
            fullPhoneNumber = documentSnapshot.id,
            description = description,
            careerData = careerData
    )
}