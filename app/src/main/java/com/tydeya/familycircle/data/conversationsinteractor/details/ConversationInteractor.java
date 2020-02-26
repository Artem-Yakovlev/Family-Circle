package com.tydeya.familycircle.data.conversationsinteractor.details;

import android.util.Log;

import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationInteractorCallback;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationInteractorObservable;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractor;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractorCallback;
import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.Conversation;

import java.util.ArrayList;
import java.util.Collections;

public class ConversationInteractor implements ConversationInteractorObservable, ConversationNetworkInteractorCallback {

    private ConversationNetworkInteractor networkInteractor;
    private ArrayList<ConversationInteractorCallback> observers;
    private ArrayList<Conversation> conversations;

    public ConversationInteractor() {
        this.observers = new ArrayList<>();
        this.conversations = new ArrayList<>();
        this.networkInteractor = new ConversationNetworkInteractorImpl(this);
        prepareConversationsData();

    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    private void prepareConversationsData() {
        networkInteractor.requireConversationsDataFromServer();
    }

    /**
     * Send message
     */

    public void sendMessage(ChatMessage chatMessage, Conversation conversation) {
        networkInteractor.sendChatMessageToServer(chatMessage, conversation);
    }

    /**
     * Notifications
     */

    @Override
    public void conversationsAllDataUpdated(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
        for (Conversation conversation : conversations) {
            Collections.sort(conversation.getChatMessages(), (o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        }
        notifyObserversConversationsDataUpdated();
        networkInteractor.setUpdateConversationsListener(this.conversations);

    }

    @Override
    public void conversationUpdate(Conversation actualConversation) {
        Collections.sort(actualConversation.getChatMessages(), (o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        for (Conversation conversation: conversations) {
            if (conversation.getDescription().getTitle().equals(actualConversation.getDescription().getTitle())) {
                conversation.setChatMessages(actualConversation.getChatMessages());
            }
        }
        notifyObserversConversationsDataUpdated();
    }

    private void notifyObserversConversationsDataUpdated() {
        for (ConversationInteractorCallback callback : observers) {
            Log.d("ASMR", "test");
            callback.conversationsDataUpdated();
        }
    }

    @Override
    public void subscribe(ConversationInteractorCallback callback) {
        if (!observers.contains(callback)) {
            observers.add(callback);
            Log.d("ASMR", "sub");
        }
    }

    @Override
    public void unsubscribe(ConversationInteractorCallback callback) {
        observers.remove(callback);
        Log.d("ASMR", "uns");
    }
}
