package com.tydeya.familycircle.domain.familyinteractor.details;

import android.util.ArrayMap;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.App;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyNetworkInteractor;
import com.tydeya.familycircle.domain.familyinteractor.abstraction.FamilyNetworkInteractorCallback;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.data.familymember.contacts.FamilyMemberContacts;
import com.tydeya.familycircle.data.familymember.description.FamilyMemberDescription;
import com.tydeya.familycircle.data.familymember.otherdata.FamilyMemberCareerData;
import com.tydeya.familycircle.domain.onlinemanager.abstraction.OnlineInteractor;
import com.tydeya.familycircle.domain.onlinemanager.details.OnlineInteractorImpl;
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_USERS_BIRTHDATE_TAG;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_USERS_COLLECTION;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_USERS_IMAGE_ADDRESS;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_USERS_NAME_TAG;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_USERS_PHONE_TAG;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_USERS_STUDY_TAG;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_USERS_WORK_TAG;
import static com.tydeya.familycircle.utils.StringExtensionsKt.ifNullToEmpty;

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

        String imageAddress = ifNullToEmpty(documentSnapshot.getString(FIRESTORE_USERS_IMAGE_ADDRESS));

        FamilyMemberDescription description = new FamilyMemberDescription(name, birthDate, imageAddress);
        FamilyMemberContacts contacts = new FamilyMemberContacts();

        String workPlace = ifNullToEmpty(documentSnapshot.getString(FIRESTORE_USERS_WORK_TAG));
        String studyPlace = ifNullToEmpty(documentSnapshot.getString(FIRESTORE_USERS_STUDY_TAG));

        FamilyMemberCareerData careerData = new FamilyMemberCareerData(studyPlace, workPlace);

        return new FamilyMember(fullPhoneNumber, description, contacts, careerData);
    }

}
