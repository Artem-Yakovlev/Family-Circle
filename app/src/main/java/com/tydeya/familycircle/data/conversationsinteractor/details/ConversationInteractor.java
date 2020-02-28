package com.tydeya.familycircle.data.conversationsinteractor.details;

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
     * Badges
     */

    public int getActualConversationBadges() {
        int numberBadges = 0;
        for (Conversation conversation : conversations) {
            numberBadges += conversation.getNumberUnreadMessages();
        }
        return numberBadges;
    }

    /**
     * Send and read message
     */

    public void sendMessage(ChatMessage chatMessage, Conversation conversation, ArrayList<String> phoneNumbers) {
        networkInteractor.sendChatMessageToServer(chatMessage, conversation, phoneNumbers);
    }

    public void readMessages(Conversation conversation) {
        networkInteractor.makeMessagesRead(conversation);
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
        for (Conversation conversation : conversations) {
            if (conversation.getDescription().getTitle().equals(actualConversation.getDescription().getTitle())) {
                conversation.setChatMessages(actualConversation.getChatMessages());
            }
        }
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
