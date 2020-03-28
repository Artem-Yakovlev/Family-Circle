package com.tydeya.familycircle.framework.editaccount.details

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.tydeya.familycircle.data.constants.FireStorage.FIRESTORAGE_PROFILE_IMAGE_DIRECTORY
import com.tydeya.familycircle.data.constants.Firebase
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.framework.editaccount.abstraction.EditAccountTool
import com.tydeya.familycircle.ui.managerpart.editprofile.details.EditableFamilyMember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*

class EditAccountToolImpl(val context: Context) : EditAccountTool {

    override suspend fun editAccountData(phoneNumber: String,
                                         editableFamilyMember: EditableFamilyMember,
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

        val fileAddress = "/$FIRESTORAGE_PROFILE_IMAGE_DIRECTORY/${FirebaseAuth.getInstance().currentUser!!.phoneNumber}.jpg"

        FirebaseStorage.getInstance().getReference(fileAddress)
                .putBytes(byteArrayOutputStream.toByteArray()).addOnSuccessListener {
                    editDataInFirebase(phoneNumber, editableFamilyMember, it.downloadUrl.toString())
                }.addOnFailureListener {
                    it.printStackTrace()
                    editDataInFirebase(phoneNumber, editableFamilyMember, "")
                }
    }

    private fun editDataInFirebase(phoneNumber: String, editableFamilyMember: EditableFamilyMember,
                                   imageAddress: String) {

        FirebaseFirestore.getInstance().collection(FIRESTORE_USERS_COLLECTION)
                .whereEqualTo(FIRESTORE_USERS_PHONE_TAG, phoneNumber).get()
                .addOnSuccessListener { querySnapshot ->
                    run {
                        querySnapshot.documents[0].reference.set(hashMapOf(
                                FIRESTORE_USERS_PHONE_TAG to phoneNumber,
                                FIRESTORE_USERS_NAME_TAG to editableFamilyMember.name,
                                FIRESTORE_USERS_BIRTHDATE_TAG to
                                        Date().apply { time = editableFamilyMember.birthdate },
                                FIRESTORE_USERS_STUDY_TAG to editableFamilyMember.studyPlace,
                                FIRESTORE_USERS_WORK_TAG to editableFamilyMember.workPlace,
                                FIRESTORE_USERS_IMAGE_ADDRESS to imageAddress) as Map<String, Any>)
                    }
                }
    }


}