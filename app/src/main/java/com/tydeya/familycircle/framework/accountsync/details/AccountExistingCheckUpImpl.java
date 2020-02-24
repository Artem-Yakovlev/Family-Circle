package com.tydeya.familycircle.framework.accountsync.details;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.framework.accountsync.abstraction.AccountExistingCheckUp;
import com.tydeya.familycircle.framework.accountsync.abstraction.AccountExistingCheckUpCallback;

public class AccountExistingCheckUpImpl implements AccountExistingCheckUp {

    private AccountExistingCheckUpCallback callbacks;


    public AccountExistingCheckUpImpl(AccountExistingCheckUpCallback callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void isAccountWithPhoneExist(String fullPhoneNumber) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        String USERS_COLLECTION = "Users";
        String PHONE_NUMBER_PROPERTY = "phone_number";

        Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
                .collection(USERS_COLLECTION).whereEqualTo(PHONE_NUMBER_PROPERTY, fullPhoneNumber).get();

        querySnapshotTask.addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.getDocuments().size() == 0) {
                callbacks.accountIsNotExist();
            } else {
                callbacks.accountIsExist(queryDocumentSnapshots);
            }
        });
    }
}
