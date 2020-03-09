package com.tydeya.familycircle.data.familyinteractor.details;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.data.familyinteractor.abstraction.FamilyNetworkInteractorCallback;
import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familymember.contacts.FamilyMemberContacts;
import com.tydeya.familycircle.domain.familymember.description.FamilyMemberDescription;
import com.tydeya.familycircle.domain.familymember.otherdata.FamilyMemberCareerData;
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring;

import java.util.ArrayList;

import static com.tydeya.familycircle.domain.constants.Firebase.FIRESTORE_USERS_BIRTHDATE_TAG;
import static com.tydeya.familycircle.domain.constants.Firebase.FIRESTORE_USERS_COLLECTION;
import static com.tydeya.familycircle.domain.constants.Firebase.FIRESTORE_USERS_NAME_TAG;
import static com.tydeya.familycircle.domain.constants.Firebase.FIRESTORE_USERS_PHONE_TAG;

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
        firebaseFirestore.collection(FIRESTORE_USERS_COLLECTION).addSnapshotListener((queryDocumentSnapshots, e) -> {
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

        String name = documentSnapshot.getString(FIRESTORE_USERS_NAME_TAG);
        long birthDate = DateRefactoring.dateToTimestamp(documentSnapshot.getDate(FIRESTORE_USERS_BIRTHDATE_TAG));
        String fullPhoneNumber = documentSnapshot.getString(FIRESTORE_USERS_PHONE_TAG);

        FamilyMemberDescription description = new FamilyMemberDescription(name, birthDate, null);
        FamilyMemberContacts contacts = new FamilyMemberContacts();
        FamilyMemberCareerData careerData = new FamilyMemberCareerData(null, null);

        return new FamilyMember(fullPhoneNumber, description, contacts, careerData);
    }

}
