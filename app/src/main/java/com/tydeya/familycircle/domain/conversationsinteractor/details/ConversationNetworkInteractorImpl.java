package com.tydeya.familycircle.domain.conversationsinteractor.details;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationNetworkInteractor;
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationNetworkInteractorCallback;
import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.oldconversation.OldConversation;
import com.tydeya.familycircle.data.oldconversation.description.details.OldConversationAttachments;
import com.tydeya.familycircle.data.oldconversation.description.details.OldConversationDescription;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_CONVERSATION_COLLECTION;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_AUTHOR_PHONE;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_COLLECTION;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_DATETIME;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_TEXT;
import static com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_MESSAGE_UNREAD_PATTERN;

public class ConversationNetworkInteractorImpl implements ConversationNetworkInteractor {

    private ConversationNetworkInteractorCallback callback;
    private String currentNumber;

    ConversationNetworkInteractorImpl(ConversationNetworkInteractorCallback callback) {
        this.currentNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        this.callback = callback;
    }

    private void parseMessagesFromSnapshot(OldConversation oldConversation, QuerySnapshot queryDocumentSnapshots) {
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

        oldConversation.setChatMessages(messages);
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

        FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<OldConversation> oldConversations = new ArrayList<>();

            for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {

                String title = queryDocumentSnapshots.getDocuments().get(i).get("FIRESTORE_CONVERSATION_NAME").toString();
                oldConversations.add(new OldConversation(new ArrayList<>(), new OldConversationDescription(title),
                        new OldConversationAttachments(), queryDocumentSnapshots.getDocuments().get(i).getId()));
            }
            getMessagesDataForAllConversations(oldConversations, queryDocumentSnapshots.getDocuments().size());
        });
    }

    private void getMessagesDataForAllConversations(ArrayList<OldConversation> oldConversations, int numbersConversations) {
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < numbersConversations; i++) {
            OldConversation oldConversation = oldConversations.get(i);

            FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION).document(oldConversations.get(i).getKey())
                    .collection(FIRESTORE_MESSAGE_COLLECTION).get().addOnSuccessListener(queryDocumentSnapshots -> {

                parseMessagesFromSnapshot(oldConversation, queryDocumentSnapshots);
                counter.getAndIncrement();

                if (counter.intValue() == numbersConversations) {
                    callback.conversationsAllDataUpdated(oldConversations);
                }

            });
        }

    }

    /**
     * Changing data listener
     */

    public void setUpdateConversationsListener(ArrayList<OldConversation> oldConversations) {

        for (OldConversation oldConversation : oldConversations) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION).document(oldConversation.getKey())
                    .collection(FIRESTORE_MESSAGE_COLLECTION).addSnapshotListener((queryDocumentSnapshots, e) -> {

                OldConversation tempOldConversation = new OldConversation(null,
                        oldConversation.getDescription(), oldConversation.getAttachments(), oldConversation.getKey());

                parseMessagesFromSnapshot(tempOldConversation, queryDocumentSnapshots);

                if (tempOldConversation.getChatMessages().size() != oldConversation.getChatMessages().size()) {
                    callback.conversationUpdate(tempOldConversation);
                }
            });
        }
    }

    /**
     * Send data to server
     */

    @Override
    public void sendChatMessageToServer(ChatMessage chatMessage, OldConversation oldConversation, ArrayList<String> phoneNumbers) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                .document(oldConversation.getKey())
                .collection(FIRESTORE_MESSAGE_COLLECTION)
                .add(parseDataFromChatMessageForServer(chatMessage, phoneNumbers));
    }

    @Override
    public void makeMessagesRead(OldConversation oldConversation) {

        FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                .document(oldConversation.getKey())
                .collection(FIRESTORE_MESSAGE_COLLECTION)
                .whereEqualTo( FIRESTORE_MESSAGE_UNREAD_PATTERN + currentNumber, true)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot querySnapshot: queryDocumentSnapshots.getDocuments()) {
                        querySnapshot.getReference().update(FIRESTORE_MESSAGE_UNREAD_PATTERN + currentNumber, false);
                    }

                    for (ChatMessage chatMessage: oldConversation.getChatMessages()) {
                        chatMessage.setViewed(true);
                    }

                    callback.conversationUpdate(oldConversation);
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
