package com.tydeya.familycircle.user;

;

public class User {


    private User() {
        /*
        families = new ArrayList<>();
        FamilyDescription testDescription = new FamilyDescription("Это тестовая семья", null);
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();


        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        //messages.add(new PersonMessage(null, "Hello", new ActiveMember("Ирина Яковлева")));
        //messages.add(new PersonMessage(null, "What are you thinking about today weather?", new ActiveMember("Елена Яковлева")));
        //messages.add(new PersonMessage(null, "Nothing...", new ActiveMember("Елена Яковлева")));
        //messages.add(new PersonMessage(null, ":)", new ActiveMember("Дмитрий Яковлев")));

        FamilyConversation conversation1 = new FamilyConversation(chatMessages, "Main conf");
        FamilyConversation conversation2 = new FamilyConversation(new ArrayList<>(), "Second conf");
        ArrayList<FamilyConversation> conversations = new ArrayList<>();
        conversations.add(conversation1);
        conversations.add(conversation2);


        Family testFamily = new Family(testDescription, null, familyMembers, conversations);
        families.add(testFamily);*/
    }

    /*
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
        //getFamily().getFamilyConversations().get(actualFamily).getMessages()
        //        .add(new PersonMessage(null, "It's very strange monologue!", userFamilyMember));
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
                ArrayList<ChatMessage> chatMessages = new ArrayList<>();

                FamilyConversation conversation = new FamilyConversation(chatMessages,
                        queryDocumentSnapshots.getDocuments().get(i).get("name").toString());
                conversations.add(conversation);

            }
            getFamily().setFamilyConversations(conversations);
            conversationUpdatedResultRecipient.conversationDataUpdated();
        });
    }*/
}
