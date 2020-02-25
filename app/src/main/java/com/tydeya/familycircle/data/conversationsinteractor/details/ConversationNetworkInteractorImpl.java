package com.tydeya.familycircle.data.conversationsinteractor.details;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractor;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractorCallback;
import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.constants.Firebase;
import com.tydeya.familycircle.domain.conversation.Conversation;
import com.tydeya.familycircle.domain.conversation.description.details.ConversationAttachments;
import com.tydeya.familycircle.domain.conversation.description.details.ConversationDescription;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ConversationNetworkInteractorImpl implements ConversationNetworkInteractor {

    private ConversationNetworkInteractorCallback callback;
    private FirebaseFirestore firebaseFirestore;

    ConversationNetworkInteractorImpl(ConversationNetworkInteractorCallback callback) {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.callback = callback;
    }

    /**
     * Require data from server
     */

    @Override
    public void requireConversationsDataFromServer() {
        getKeysForMessages();
    }

    private void getKeysForMessages() {
        firebaseFirestore.collection("/Conversations").get().addOnSuccessListener(queryDocumentSnapshots -> {

            ArrayList<Conversation> conversations = new ArrayList<>();
            ArrayList<String> keys = new ArrayList<>();

            for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {

                String title = queryDocumentSnapshots.getDocuments().get(i).get("name").toString();
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

            firebaseFirestore.collection("/Conversations").document(conversations.get(i).getKey())
                    .collection("Messages").get().addOnSuccessListener(queryDocumentSnapshots -> {

                ArrayList<ChatMessage> messages = new ArrayList<>();

                for (int j = 0; j < queryDocumentSnapshots.getDocuments().size(); j++) {

                    String text = queryDocumentSnapshots.getDocuments()
                            .get(j).get(Firebase.FIRESTORE_MESSAGE_TEXT).toString();

                    String phoneNumber = queryDocumentSnapshots.getDocuments()
                            .get(j).get(Firebase.FIRESTORE_MESSAGE_AUTHOR_PHONE).toString();

                    Date dateTime = queryDocumentSnapshots.getDocuments()
                            .get(j).getDate(Firebase.FIRESTORE_MESSAGE_DATETIME);

                    messages.add(new ChatMessage(phoneNumber, text, dateTime));
                }

                conversation.setChatMessages(messages);
                counter.getAndIncrement();

                if (counter.intValue() == numbersConversations) {
                    callback.conversationsDataUpdated(conversations);
                }

            });
        }

    }


    /**
     * Send data to server
     */

    @Override
    public void sendChatMessageToServer(ChatMessage chatMessage, Conversation conversation) {
        firebaseFirestore.collection("/Conversations")
                .document(conversation.getKey())
                .collection("Messages").add(parseDataFromChatMessageForServer(chatMessage));
    }

    private Map<String, Object> parseDataFromChatMessageForServer(ChatMessage chatMessage) {
        Map<String, Object> firebaseData = new HashMap<>();

        firebaseData.put(Firebase.FIRESTORE_MESSAGE_TEXT, chatMessage.getText());
        firebaseData.put(Firebase.FIRESTORE_MESSAGE_AUTHOR_PHONE, chatMessage.getAuthorPhoneNumber());
        firebaseData.put(Firebase.FIRESTORE_MESSAGE_DATETIME, chatMessage.getDateTime());

        return firebaseData;
    }

}
