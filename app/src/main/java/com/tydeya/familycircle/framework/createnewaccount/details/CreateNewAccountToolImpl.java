package com.tydeya.familycircle.framework.createnewaccount.details;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountTool;
import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountToolCallback;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.tydeya.familycircle.data.constants.FireStorage.FIRESTORAGE_PROFILE_IMAGE_DIRECTORY;
import static com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_BIRTHDATE_TAG;
import static com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_COLLECTION;
import static com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_IMAGE_ADDRESS;
import static com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_LAST_ONLINE;
import static com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_NAME_TAG;
import static com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_USERS_PHONE_TAG;

public class CreateNewAccountToolImpl implements CreateNewAccountTool {

    private CreateNewAccountToolCallback callback;

    public CreateNewAccountToolImpl(CreateNewAccountToolCallback callback) {
        this.callback = callback;
    }

    @Override
    public void create(String fullPhoneNumber, String name, long birthDate, Bitmap imageBitmap) {

        if (imageBitmap != null) {
            prepareImageUriForServer(fullPhoneNumber, name, birthDate, imageBitmap);
        } else {
            createAccount(createDataForFirestore(fullPhoneNumber, name, birthDate, ""));
        }

    }

    private void prepareImageUriForServer(String fullPhoneNumber, String name,
                                          long birthDate, Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        final String fileAddress = "/" + FIRESTORAGE_PROFILE_IMAGE_DIRECTORY + "/"
                + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() + ".jpg";

        FirebaseStorage.getInstance().getReference(fileAddress)
                .putBytes(byteArrayOutputStream.toByteArray())
                .addOnSuccessListener(taskSnapshot -> {

//                    createAccount(createDataForFirestore(fullPhoneNumber, name, birthDate,
//                            Objects.requireNonNull(taskSnapshot.getDownloadUrl()).toString()));

                })
                .addOnFailureListener(e -> {

                    e.printStackTrace();
                    createAccount(createDataForFirestore(fullPhoneNumber, name, birthDate, ""));

                });
    }

    private void createAccount(Map<String, Object> dataForFirestore) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_USERS_COLLECTION).add(dataForFirestore)
                .addOnSuccessListener(documentReference -> {
                    callback.accountCreatedSuccessful();
                })
                .addOnFailureListener(e -> {
                    callback.accountCreateFailure();
                });
    }

    private Map<String, Object> createDataForFirestore(String fullPhoneNumber, String name,
                                                       long birthDate, String imageAddress) {

        Map<String, Object> userDataForFirestore = new HashMap<>();

        userDataForFirestore.put(FIRESTORE_USERS_PHONE_TAG, fullPhoneNumber);
        userDataForFirestore.put(FIRESTORE_USERS_NAME_TAG, name);
        userDataForFirestore.put(FIRESTORE_USERS_BIRTHDATE_TAG, new Date(birthDate));
        userDataForFirestore.put(FIRESTORE_USERS_LAST_ONLINE, new Date());
        userDataForFirestore.put(FIRESTORE_USERS_IMAGE_ADDRESS, imageAddress);

        return userDataForFirestore;
    }
}
