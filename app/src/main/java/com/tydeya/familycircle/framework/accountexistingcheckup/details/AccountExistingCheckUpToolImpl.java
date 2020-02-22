package com.tydeya.familycircle.framework.accountexistingcheckup.details;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.framework.accountexistingcheckup.abstraction.AccountExistingCheckUpRecipient;
import com.tydeya.familycircle.framework.accountexistingcheckup.abstraction.AccountExistingCheckUpTool;

public class AccountExistingCheckUpToolImpl implements AccountExistingCheckUpTool {

    @Override
    public void isExist(String phoneNumber, AccountExistingCheckUpRecipient recipient){
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Task<QuerySnapshot> querySnapshotTask = firebaseFirestore
                .collection("Users").whereEqualTo("phone_number", phoneNumber).get();

        querySnapshotTask.addOnSuccessListener(queryDocumentSnapshots -> {

            if (queryDocumentSnapshots.getDocuments().size() == 0) {
                recipient.accountExistingCheckUpResult(false);
            } else {
                recipient.accountExistingCheckUpResult(true);
            }
        }).addOnFailureListener(e -> {
            Log.d("ASMR", e.toString());
        });
    }
}
