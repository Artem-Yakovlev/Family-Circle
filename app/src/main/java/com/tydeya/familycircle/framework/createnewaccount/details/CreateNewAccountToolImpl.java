package com.tydeya.familycircle.framework.createnewaccount.details;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountTool;
import com.tydeya.familycircle.framework.createnewaccount.abstraction.CreateNewAccountToolCallback;

import java.util.HashMap;
import java.util.Map;

import static com.tydeya.familycircle.constants.Firebase.FIRESTORE_BIRTHDATE_TAG;
import static com.tydeya.familycircle.constants.Firebase.FIRESTORE_NAME_TAG;
import static com.tydeya.familycircle.constants.Firebase.FIRESTORE_PHONE_TAG;
import static com.tydeya.familycircle.constants.Firebase.FIRESTORE_USERS_COLLECTION;

public class CreateNewAccountToolImpl implements CreateNewAccountTool {

    private CreateNewAccountToolCallback callback;

    public CreateNewAccountToolImpl(CreateNewAccountToolCallback callback) {
        this.callback = callback;
    }

    @Override
    public void create(String fullPhoneNumber, String name, String birthDate) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> dataForFirestore = createDataForFirestore(fullPhoneNumber, name, birthDate);

        firebaseFirestore.collection(FIRESTORE_USERS_COLLECTION).add(dataForFirestore)
                .addOnSuccessListener(documentReference -> {
                    callback.accountCreatedSuccessful();
                })
                .addOnFailureListener(e -> {
                    callback.accountCreateFailure();
                });
    }

    private Map<String, Object> createDataForFirestore(String fullPhoneNumber, String name, String birthDate) {
        Map<String, Object> userDataForFirestore = new HashMap<>();

        userDataForFirestore.put(FIRESTORE_PHONE_TAG, fullPhoneNumber);
        userDataForFirestore.put(FIRESTORE_NAME_TAG, name);
        userDataForFirestore.put(FIRESTORE_BIRTHDATE_TAG, birthDate);

        return userDataForFirestore;
    }
}
