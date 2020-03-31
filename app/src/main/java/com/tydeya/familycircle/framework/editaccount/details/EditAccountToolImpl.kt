package com.tydeya.familycircle.framework.editaccount.details

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.tydeya.familycircle.App
import com.tydeya.familycircle.data.constants.FireStorage.FIRESTORAGE_PROFILE_IMAGE_DIRECTORY
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.framework.editaccount.abstraction.EditAccountTool
import com.tydeya.familycircle.ui.managerpart.editprofile.details.EditableFamilyMember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class EditAccountToolImpl(val context: Context) : EditAccountTool {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    init {
        App.getComponent().injectTool(this)
    }

    override suspend fun editAccountData(phoneNumber: String,
                                         editableFamilyMember: EditableFamilyMember,
                                         editableBitmap: Bitmap?) {
        GlobalScope.launch(Dispatchers.Default) {

            val imageAddress = FamilyAssistantImpl(familyInteractor.actualFamily)
                    .getUserByPhone(FirebaseAuth.getInstance().currentUser!!.phoneNumber)
                    .description.imageAddress

            if (editableBitmap != null) {
                prepareImageUriForServer(phoneNumber, editableFamilyMember, editableBitmap)
            } else {
                editDataInFirebase(phoneNumber, editableFamilyMember, imageAddress)
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
                                FIRESTORE_USERS_LAST_ONLINE to Date(),
                                FIRESTORE_USERS_IMAGE_ADDRESS to imageAddress) as Map<String, Any>)
                    }
                }
    }


}