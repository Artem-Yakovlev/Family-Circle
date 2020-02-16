package com.tydeya.familycircle.user;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.conversationpart.ConversationUpdatedResultRecipient;
import com.tydeya.familycircle.family.Family;
import com.tydeya.familycircle.family.conversation.FamilyConversation;
import com.tydeya.familycircle.family.conversation.messages.Message;
import com.tydeya.familycircle.family.conversation.messages.PersonMessage;
import com.tydeya.familycircle.family.description.FamilyDescription;
import com.tydeya.familycircle.family.member.ActiveMember;
import com.tydeya.familycircle.family.member.FamilyMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class User {

    private ActiveMember userFamilyMember;
    private int actualFamily = 0;
    private ArrayList<Family> families;
    private Settings userSettings;

    private static volatile User instance;


    private User() {

        families = new ArrayList<>();
        FamilyDescription testDescription = new FamilyDescription("Это тестовая семья", null);
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();


        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new PersonMessage(null, "Hello", new ActiveMember("Ирина Яковлева")));
        messages.add(new PersonMessage(null, "What are you thinking about today weather?", new ActiveMember("Елена Яковлева")));
        messages.add(new PersonMessage(null, "Nothing...", new ActiveMember("Елена Яковлева")));
        messages.add(new PersonMessage(null, ":)", new ActiveMember("Дмитрий Яковлев")));

        FamilyConversation conversation1 = new FamilyConversation(messages, "Main conf");
        FamilyConversation conversation2 = new FamilyConversation(new ArrayList<>(), "Second conf");
        ArrayList<FamilyConversation> conversations = new ArrayList<>();
        conversations.add(conversation1);
        conversations.add(conversation2);


        Family testFamily = new Family(testDescription, null, familyMembers, conversations);
        families.add(testFamily);
    }

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void updateDataFromServer(String phoneNumber, DataUpdatedResultRecipient recipient) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> userDataTask = firebaseFirestore.collection("/Users")
                .whereEqualTo("phone_number", phoneNumber).get();
        userDataTask.addOnSuccessListener(queryDocumentSnapshots -> {
            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
            updateUserMemberData(documentSnapshot);

        });

        Task<QuerySnapshot> familyMemberDataTask = firebaseFirestore.collection("/Users").get();
        familyMemberDataTask.addOnSuccessListener(queryDocumentSnapshots -> {
            updateFamilyMemberData(queryDocumentSnapshots, phoneNumber, recipient);
        });
    }

    private void updateUserMemberData(DocumentSnapshot documentSnapshot) {
        ActiveMember.Builder activeMemberBuilder = new ActiveMember.Builder();
        //TODO fix birthDate
        activeMemberBuilder.setBirthDate(new GregorianCalendar(2001, Calendar.JANUARY, 28));
        activeMemberBuilder.setName(documentSnapshot.get("name").toString());
        activeMemberBuilder.setPhoneNumber(documentSnapshot.get("phone_number").toString());
        userFamilyMember = activeMemberBuilder.build();
        getFamily().getFamilyConversations().get(actualFamily).getMessages()
                .add(new PersonMessage(null, "It's very strange monologue!", userFamilyMember));
    }

    private void updateFamilyMemberData(@NonNull QuerySnapshot queryDocumentSnapshots, String phoneNumber, DataUpdatedResultRecipient recipient) {
        getFamily().setFamilyMembers(new ArrayList<>());
        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
            if (!documentSnapshot.get("phone_number").toString().equals(phoneNumber)) {
                getFamily().getFamilyMembers().add(new ActiveMember(documentSnapshot.get("name").toString()));
            }
        }
        recipient.familyMemberUpdated();
    }

    public void updateConversationData(ConversationUpdatedResultRecipient conversationUpdatedResultRecipient) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> conversationDataTask = firebaseFirestore.collection("/Conversations").get();

        conversationDataTask.addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<FamilyConversation> conversations = new ArrayList<>();
            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                ArrayList<Message> messages = new ArrayList<>();

                FamilyConversation conversation = new FamilyConversation(messages,
                        queryDocumentSnapshots.getDocuments().get(i).get("name").toString());
                conversations.add(conversation);
            }
            getFamily().setFamilyConversations(conversations);
            conversationUpdatedResultRecipient.conversationDataUpdated();
        });
    }



    public ActiveMember getUserFamilyMember() {
        return userFamilyMember;
    }

    public void setUserFamilyMember(ActiveMember userFamilyMember) {
        this.userFamilyMember = userFamilyMember;
    }

    public Family getFamily() {
        return families.get(actualFamily);
    }
}
