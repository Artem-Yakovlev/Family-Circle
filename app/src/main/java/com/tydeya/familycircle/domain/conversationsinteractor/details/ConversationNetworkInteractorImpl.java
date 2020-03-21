package com.tydeya.familycircle.domain.conversationsinteractor.details;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationNetworkInteractor;
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationNetworkInteractorCallback;
import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.conversation.Conversation;
import com.tydeya.familycircle.data.conversation.description.details.ConversationAttachments;
import com.tydeya.familycircle.data.conversation.description.details.ConversationDescription;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_CONVERSATION_COLLECTION;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_CONVERSATION_NAME;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_AUTHOR_PHONE;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_COLLECTION;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_DATETIME;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_TEXT;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_UNREAD_PATTERN;

public class ConversationNetworkInteractorImpl implements ConversationNetworkInteractor {

    private ConversationNetworkInteractorCallback callback;
    private FirebaseFirestore firebaseFirestore;
    private String currentNumber;

    ConversationNetworkInteractorImpl(ConversationNetworkInteractorCallback callback) {
        this.currentNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.callback = callback;
    }

    private void parseMessagesFromSnapshot(Conversation conversation, QuerySnapshot queryDocumentSnapshots) {
        ArrayList<ChatMessage> messages = new ArrayList<>();

        for (int j = 0; j < queryDocumentSnapshots.getDocuments().size(); j++) {

            String text = queryDocumentSnapshots.getDocuments()
                    .get(j).get(FIRESTORE_MESSAGE_TEXT).toString();

            String phoneNumber = queryDocumentSnapshots.getDocuments()
                    .get(j).get(FIRESTORE_MESSAGE_AUTHOR_PHONE).toString();

            Date dateTime = queryDocumentSnapshots.getDocuments()
                    .get(j).getDate(FIRESTORE_MESSAGE_DATETIME);

            boolean viewed = parseViewedMessage(queryDocumentSnapshots.getDocuments().get(j));

            messages.add(new ChatMessage(phoneNumber, text, dateTime, !viewed));
        }

        conversation.setChatMessages(messages);
    }

    private boolean parseViewedMessage(DocumentSnapshot documentSnapshot) {
        Boolean unread = documentSnapshot.getBoolean(FIRESTORE_MESSAGE_UNREAD_PATTERN + currentNumber);
        return unread != null && unread;
    }

    /**
     * Require data from server
     */

    @Override
    public void requireConversationsDataFromServer() {
        getKeysForMessages();
    }

    private void getKeysForMessages() {
        firebaseFirestore.collection(FIRESTORE_CONVERSATION_COLLECTION).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Conversation> conversations = new ArrayList<>();

            for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {

                String title = queryDocumentSnapshots.getDocuments().get(i).get(FIRESTORE_CONVERSATION_NAME).toString();
                conversations.add(new Conversation(new ArrayList<>(), new ConversationDescription(title),
                        new ConversationAttachments(), queryDocumentSnapshots.getDocuments().get(i).getId()));
            }
            getMessagesDataForAllConversations(conversations, queryDocumentSnapshots.getDocuments().size());
        });
    }

    private void getMessagesDataForAllConversations(ArrayList<Conversation> conversations, int numbersConversations) {
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < numbersConversations; i++) {
            Conversation conversation = conversations.get(i);

            firebaseFirestore.collection(FIRESTORE_CONVERSATION_COLLECTION).document(conversations.get(i).getKey())
                    .collection(FIRESTORE_MESSAGE_COLLECTION).get().addOnSuccessListener(queryDocumentSnapshots -> {

                parseMessagesFromSnapshot(conversation, queryDocumentSnapshots);
                counter.getAndIncrement();

                if (counter.intValue() == numbersConversations) {
                    callback.conversationsAllDataUpdated(conversations);
                }

            });
        }

    }

    /**
     * Changing data listener
     */

    public void setUpdateConversationsListener(ArrayList<Conversation> conversations) {

        for (Conversation conversation : conversations) {
            firebaseFirestore.collection(FIRESTORE_CONVERSATION_COLLECTION).document(conversation.getKey())
                    .collection(FIRESTORE_MESSAGE_COLLECTION).addSnapshotListener((queryDocumentSnapshots, e) -> {

                Conversation tempConversation = new Conversation(null,
                        conversation.getDescription(), conversation.getAttachments(), conversation.getKey());

                parseMessagesFromSnapshot(tempConversation, queryDocumentSnapshots);

                if (tempConversation.getChatMessages().size() != conversation.getChatMessages().size()) {
                    callback.conversationUpdate(tempConversation);
                }
            });
        }
    }

    /**
     * Send data to server
     */

    @Override
    public void sendChatMessageToServer(ChatMessage chatMessage, Conversation conversation, ArrayList<String> phoneNumbers) {
        firebaseFirestore.collection(FIRESTORE_CONVERSATION_COLLECTION)
                .document(conversation.getKey())
                .collection(FIRESTORE_MESSAGE_COLLECTION)
                .add(parseDataFromChatMessageForServer(chatMessage, phoneNumbers));
    }

    @Override
    public void makeMessagesRead(Conversation conversation) {

        firebaseFirestore.collection(FIRESTORE_CONVERSATION_COLLECTION)
                .document(conversation.getKey())
                .collection(FIRESTORE_MESSAGE_COLLECTION)
                .whereEqualTo( FIRESTORE_MESSAGE_UNREAD_PATTERN + currentNumber, true)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot querySnapshot: queryDocumentSnapshots.getDocuments()) {
                        querySnapshot.getReference().update(FIRESTORE_MESSAGE_UNREAD_PATTERN + currentNumber, false);
                    }

                    for (ChatMessage chatMessage: conversation.getChatMessages()) {
                        chatMessage.setViewed(true);
                    }

                    callback.conversationUpdate(conversation);
                });
    }

    private Map<String, Object> parseDataFromChatMessageForServer(ChatMessage chatMessage, ArrayList<String> phoneNumbers) {
        Map<String, Object> firebaseData = new HashMap<>();

        firebaseData.put(FIRESTORE_MESSAGE_TEXT, chatMessage.getText());
        firebaseData.put(FIRESTORE_MESSAGE_AUTHOR_PHONE, chatMessage.getAuthorPhoneNumber());
        firebaseData.put(FIRESTORE_MESSAGE_DATETIME, chatMessage.getDateTime());

        for (String phoneNumber: phoneNumbers) {
            firebaseData.put(FIRESTORE_MESSAGE_UNREAD_PATTERN + phoneNumber, true);
        }

        return firebaseData;
    }

}
