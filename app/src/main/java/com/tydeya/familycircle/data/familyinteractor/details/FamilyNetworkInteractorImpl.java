package com.tydeya.familycircle.data.familyinteractor.details;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractorCallback;
import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familymember.contacts.details.FamilyMemberContacts;
import com.tydeya.familycircle.domain.familymember.description.details.FamilyMemberDescription;

import java.util.ArrayList;

public class FamilyNetworkInteractorImpl implements FamilyNetworkInteractor {

    private FirebaseFirestore firebaseFirestore;

    private FamilyNetworkInteractorCallback callback;

    FamilyNetworkInteractorImpl(FamilyNetworkInteractorCallback callback) {
        this.callback = callback;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void requireMembersDataFromServer() {
        Task<QuerySnapshot> familyMemberDataTask = firebaseFirestore.collection("/Users").get();
        familyMemberDataTask.addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<FamilyMember> members = getMembersBySnapshot(queryDocumentSnapshots);
            callback.memberDataFromServerUpdate(members);
        });
    }

    private ArrayList<FamilyMember> getMembersBySnapshot(QuerySnapshot querySnapshots) {
        ArrayList<FamilyMember> members = new ArrayList<>();
        for (int i = 0; i < querySnapshots.getDocuments().size(); i++) {
            members.add(createMemberByData(querySnapshots.getDocuments().get(i)));
        }
        return members;
    }

    private FamilyMember createMemberByData(DocumentSnapshot documentSnapshot) {

        FamilyMemberDescription description =
                new FamilyMemberDescription(documentSnapshot.get("name").toString(), null, null);
        FamilyMemberContacts contacts = new FamilyMemberContacts(documentSnapshot.get("phone_number").toString());

        return new FamilyMember(description, contacts);
    }
}
