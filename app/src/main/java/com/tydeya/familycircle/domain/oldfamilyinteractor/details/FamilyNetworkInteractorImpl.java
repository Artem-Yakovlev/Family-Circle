package com.tydeya.familycircle.domain.oldfamilyinteractor.details;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familyinteraction.FamilyMemberFirestoreUtilsKt;
import com.tydeya.familycircle.domain.oldfamilyinteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.domain.oldfamilyinteractor.abstraction.FamilyNetworkInteractorCallback;

import java.util.ArrayList;

import static com.tydeya.familycircle.data.constants.FireStore.USERS_COLLECTION;

public class FamilyNetworkInteractorImpl implements FamilyNetworkInteractor {

    private FirebaseFirestore firebaseFirestore;

    private FamilyNetworkInteractorCallback callback;

    FamilyNetworkInteractorImpl(FamilyNetworkInteractorCallback callback) {
        this.callback = callback;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    /**
     * Require data from server
     */

    @Override
    public void requireMembersDataFromServer() {
        firebaseFirestore.collection(USERS_COLLECTION).addSnapshotListener((queryDocumentSnapshots, e) -> {
            ArrayList<FamilyMember> members = getMembersBySnapshot(queryDocumentSnapshots);
            callback.memberDataFromServerUpdate(members);
        });
    }

    private ArrayList<FamilyMember> getMembersBySnapshot(QuerySnapshot querySnapshots) {
        ArrayList<FamilyMember> members = new ArrayList<>();
        for (int i = 0; i < querySnapshots.getDocuments().size(); i++) {
            FamilyMemberFirestoreUtilsKt.convertFirestoreDataToFamilyMember(
                    querySnapshots.getDocuments().get(i)
            );
        }
        return members;
    }

}
