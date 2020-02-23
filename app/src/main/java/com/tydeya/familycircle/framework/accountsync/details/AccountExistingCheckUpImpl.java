package com.tydeya.familycircle.framework.accountsync.details;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.framework.accountsync.abstraction.AccountExistingCheckUp;
import com.tydeya.familycircle.framework.accountsync.abstraction.AccountExistingCheckUpCallbacks;

public class AccountExistingCheckUpImpl implements AccountExistingCheckUp {

    private AccountExistingCheckUpCallbacks callbacks;

    public AccountExistingCheckUpImpl(AccountExistingCheckUpCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void isAccountWithPhoneExist(String fullPhoneNumber) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
                .collection("Users").whereEqualTo("phone_number", fullPhoneNumber).get();

        querySnapshotTask.addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.getDocuments().size() == 0) {
                callbacks.accountIsNotExist();
            } else {
                callbacks.accountIsExist(queryDocumentSnapshots);
            }
        });
    }
}
