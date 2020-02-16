package com.tydeya.familycircle.synchronization.accountcreate;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.DateRefactoring;
import com.tydeya.familycircle.family.member.ActiveMember;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class CreateSyncAccountTool {

    private WeakReference<SyncAccountCreatedRecipient> syncAccountCreatedRecipient;
    private static String FIRESTORE_PHONE_TAG = "phone_number";
    private static String FIRESTORE_NAME_TAG = "name";
    private static String FIRESTORE_BIRTHDATE_TAG = "birthdate";

    public CreateSyncAccountTool(WeakReference<SyncAccountCreatedRecipient> syncAccountCreatedRecipient) {
        this.syncAccountCreatedRecipient = syncAccountCreatedRecipient;
    }

    public void CreateAccount(ActiveMember userMember) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Map<String, Object> userDataForFirestore = createDataForFirestore(userMember);


        Task querySnapshotTask = firebaseFirestore.collection("Users").add(userDataForFirestore)
                .addOnSuccessListener(documentReference -> {
                    syncAccountCreatedRecipient.get().accountSuccessfullyCreated();
                })
                .addOnFailureListener(e -> {
                    syncAccountCreatedRecipient.get().accountCreationFailed(e);
                });
    }

    private Map<String, Object> createDataForFirestore(ActiveMember userMember) {
        Map<String, Object> userDataForFirestore = new HashMap<>();
        userDataForFirestore.put(FIRESTORE_PHONE_TAG, userMember.getContact().getPhoneNumber());
        userDataForFirestore.put(FIRESTORE_NAME_TAG, userMember.getName());
        userDataForFirestore.put(FIRESTORE_BIRTHDATE_TAG, DateRefactoring
                .getDateLocaleText(userMember.getDescription().getBirthDate()));
        return userDataForFirestore;
    }
}
