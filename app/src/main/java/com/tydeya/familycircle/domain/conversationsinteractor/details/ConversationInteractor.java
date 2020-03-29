package com.tydeya.familycircle.domain.conversationsinteractor.details;

import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationInteractorCallback;
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationInteractorObservable;
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationNetworkInteractor;
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationNetworkInteractorCallback;
import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.oldconversation.OldConversation;

import java.util.ArrayList;
import java.util.Collections;

public class ConversationInteractor implements ConversationInteractorObservable, ConversationNetworkInteractorCallback {

    private ConversationNetworkInteractor networkInteractor;
    private ArrayList<ConversationInteractorCallback> observers;
    private ArrayList<OldConversation> oldConversations;

    public ConversationInteractor() {
        this.observers = new ArrayList<>();
        this.oldConversations = new ArrayList<>();
        this.networkInteractor = new ConversationNetworkInteractorImpl(this);
        prepareConversationsData();

    }

    public ArrayList<OldConversation> getOldConversations() {
        return oldConversations;
    }

    private void prepareConversationsData() {
        networkInteractor.requireConversationsDataFromServer();
    }

    /**
     * Badges
     */

    public int getActualConversationBadges() {
        int numberBadges = 0;
        for (OldConversation oldConversation : oldConversations) {
            numberBadges += oldConversation.getNumberUnreadMessages();
        }
        return numberBadges;
    }

    /**
     * Send and read message
     */

    public void sendMessage(ChatMessage chatMessage, OldConversation oldConversation, ArrayList<String> phoneNumbers) {
        networkInteractor.sendChatMessageToServer(chatMessage, oldConversation, phoneNumbers);
        conversationPositionSort(oldConversations);
    }

    public void readMessages(OldConversation oldConversation) {
        networkInteractor.makeMessagesRead(oldConversation);
    }

    /**
     * Notifications
     */

    @Override
    public void conversationsAllDataUpdated(ArrayList<OldConversation> oldConversations) {
        this.oldConversations = oldConversations;
        for (OldConversation oldConversation : oldConversations) {
            Collections.sort(oldConversation.getChatMessages(), (o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        }
        conversationPositionSort(oldConversations);
        notifyObserversConversationsDataUpdated();
        networkInteractor.setUpdateConversationsListener(this.oldConversations);
    }

    @Override
    public void conversationUpdate(OldConversation actualOldConversation) {
        Collections.sort(actualOldConversation.getChatMessages(), (o1, o2) -> o1.getDateTime().compareTo(o2.getDateTime()));
        for (OldConversation oldConversation : oldConversations) {
            if (oldConversation.getDescription().getTitle().equals(actualOldConversation.getDescription().getTitle())) {
                oldConversation.setChatMessages(actualOldConversation.getChatMessages());
            }
        }
        conversationPositionSort(oldConversations);
        notifyObserversConversationsDataUpdated();
    }

    /**
     * Utils
     * */

    private void conversationPositionSort(ArrayList<OldConversation> oldConversations) {
        Collections.sort(oldConversations, (o1, o2) -> {
            if (o1.getLastMessage() != null && o2.getLastMessage() != null) {
                return o2.getLastMessage().getDateTime().compareTo(o1.getLastMessage().getDateTime());
            }
            return 0;
        });
    }

    /**
     * Callbacks
     * */

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
