package com.tydeya.familycircle.synchronization.accountexisting;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.ref.WeakReference;

public class AccountPhoneSynchronizationTool {

    private WeakReference<AccountIsExistResultRecipient> accountIsExistResultRecipient;

    public AccountPhoneSynchronizationTool(WeakReference<AccountIsExistResultRecipient> accountIsExistResultRecipient) {
        this.accountIsExistResultRecipient = accountIsExistResultRecipient;
    }

    public void isAccountWithPhoneExist(String phoneNumber) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
                .collection("Users").whereEqualTo("phone_number", phoneNumber).get();

        querySnapshotTask.addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.getDocuments().size() == 0) {
                accountIsExistResultRecipient.get().isNotExist();
            } else {
                accountIsExistResultRecipient.get().isExist(queryDocumentSnapshots);
            }
        });
        querySnapshotTask.addOnFailureListener(e -> {
            accountIsExistResultRecipient.get().isError(e);
        });
    }
}
