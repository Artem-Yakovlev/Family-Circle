package com.tydeya.familycircle.data.conversationsinteractor.details;

import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationInteractorCallback;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationInteractorObservable;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractor;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationNetworkInteractorCallback;
import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.Conversation;

import java.util.ArrayList;

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
     * */

    public void sendMessage(ChatMessage chatMessage, Conversation conversation) {
        networkInteractor.sendChatMessageToServer(chatMessage, conversation);
    }

    /**
     * Notifications
     */

    @Override
    public void conversationsDataUpdated(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
        notifyObserversConversationsDataUpdated();

    }

    private void notifyObserversConversationsDataUpdated() {
        for (ConversationInteractorCallback callback : observers) {
            callback.conversationsDataUpdated();
        }
    }

    @Override
    public void subscribe(ConversationInteractorCallback callback) {
        if (!observers.contains(callback)) {
            observers.add(callback);
        }
    }

    @Override
    public void unsubscribe(ConversationInteractorCallback callback) {
        observers.remove(callback);
    }
}
