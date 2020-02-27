package com.tydeya.familycircle.data.familyinteractor.details;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractorCallback;
import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familymember.contacts.FamilyMemberContacts;
import com.tydeya.familycircle.domain.familymember.description.FamilyMemberDescription;

import java.util.ArrayList;

import static com.tydeya.familycircle.domain.constants.Firebase.FIRESTORE_CONVERSATION_NAME;

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
                new FamilyMemberDescription(documentSnapshot.get(FIRESTORE_CONVERSATION_NAME).toString(), null, null);
        FamilyMemberContacts contacts = new FamilyMemberContacts();

        return new FamilyMember(documentSnapshot.get("phone_number").toString(), description, contacts);
    }

}
