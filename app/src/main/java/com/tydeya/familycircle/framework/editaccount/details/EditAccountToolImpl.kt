package com.tydeya.familycircle.framework.editaccount.details

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tydeya.familycircle.data.constants.FireStorage.FIRESTORAGE_PROFILE_IMAGE_DIRECTORY
import com.tydeya.familycircle.data.constants.FireStore.USERS_BIRTH_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.USERS_IMAGE_PATH
import com.tydeya.familycircle.data.constants.FireStore.USERS_LAST_ONLINE
import com.tydeya.familycircle.data.constants.FireStore.USERS_NAME_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_PHONE_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_STUDY_TAG
import com.tydeya.familycircle.data.constants.FireStore.USERS_WORK_TAG
import com.tydeya.familycircle.data.familymember.EditableFamilyMember
import com.tydeya.familycircle.framework.editaccount.abstraction.EditAccountTool
import com.tydeya.familycircle.utils.extensions.getUserPhone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*

class EditAccountToolImpl(val context: Context) : EditAccountTool {

    override suspend fun editAccountData(phoneNumber: String, editableFamilyMember: EditableFamilyMember,
                                         editableBitmap: Bitmap?) {
        GlobalScope.launch(Dispatchers.Default) {

            if (editableBitmap != null) {
                prepareImageUriForServer(phoneNumber, editableFamilyMember, editableBitmap)
            } else {
                editDataInFirebase(phoneNumber, editableFamilyMember, "")
            }
        }
    }

    private fun prepareImageUriForServer(phoneNumber: String,
                                         editableFamilyMember: EditableFamilyMember,
                                         bitmap: Bitmap) {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)

        val fileAddress = "/$FIRESTORAGE_PROFILE_IMAGE_DIRECTORY/${getUserPhone()}.jpg"
        val firestorageReference = FirebaseStorage.getInstance().getReference(fileAddress)

        firestorageReference.putBytes(byteArrayOutputStream.toByteArray()).addOnSuccessListener {
            firestorageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                editDataInFirebase(phoneNumber, editableFamilyMember, downloadUri.toString())
            }.addOnFailureListener {
                editDataInFirebase(phoneNumber, editableFamilyMember, "")
            }

        }.addOnFailureListener {
            editDataInFirebase(phoneNumber, editableFamilyMember, "")
        }

    }


    private fun editDataInFirebase(phoneNumber: String,
                                   editableFamilyMember: EditableFamilyMember,
                                   imageAddress: String) {

        val firebaseHashMap = hashMapOf(
                USERS_PHONE_TAG to phoneNumber,
                USERS_NAME_TAG to editableFamilyMember.name,
                USERS_BIRTH_TAG to
                        Date().apply { time = editableFamilyMember.birthdate },
                USERS_STUDY_TAG to editableFamilyMember.studyPlace,
                USERS_WORK_TAG to editableFamilyMember.workPlace,
                USERS_LAST_ONLINE to Date())

        if (imageAddress != "") {
            firebaseHashMap[USERS_IMAGE_PATH] = imageAddress
        }

        FirebaseFirestore.getInstance().collection(USERS_COLLECTION)
                .document(phoneNumber)
                .update(firebaseHashMap as Map<String, Any>)
    }
}

