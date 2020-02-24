package com.tydeya.familycircle.data.conversationsinteractor.details;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractor;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractorCallback;
import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.Conversation;
import com.tydeya.familycircle.domain.conversation.description.details.ConversationAttachments;
import com.tydeya.familycircle.domain.conversation.description.details.ConversationDescription;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ConversationNetworkInteractorImpl implements ConversationNetworkInteractor {

    private ConversationNetworkInteractorCallback callback;
    private FirebaseFirestore firebaseFirestore;

    ConversationNetworkInteractorImpl(ConversationNetworkInteractorCallback callback) {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.callback = callback;
    }

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
                        new ConversationAttachments()));

                String key = queryDocumentSnapshots.getDocuments().get(i).getId();
                keys.add(key);
            }
            getMessagesDataForAllConversations(conversations, keys, queryDocumentSnapshots.getDocuments().size());
        });
    }

    private void getMessagesDataForAllConversations(ArrayList<Conversation> conversations, ArrayList<String> keys,
                                                    int numbersConversations) {
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < numbersConversations; i++) {
            Conversation conversation = conversations.get(i);

            firebaseFirestore.collection("/Conversations").document(keys.get(i)).collection("Messages").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {

                        ArrayList<ChatMessage> messages = new ArrayList<>();

                        for (int j = 0; j < queryDocumentSnapshots.getDocuments().size(); j++) {
                            String text = queryDocumentSnapshots.getDocuments().get(j).get("text").toString();
                            String phoneNumber = queryDocumentSnapshots.getDocuments()
                                    .get(j).get("authorPhoneNumber").toString();
                            messages.add(new ChatMessage(phoneNumber, text, null));
                        }
                        conversation.setChatMessages(messages);
                        counter.getAndIncrement();

                        if (counter.intValue() == numbersConversations) {
                            callback.conversationsDataUpdated(conversations);
                        }

                    });
        }

    }


}
