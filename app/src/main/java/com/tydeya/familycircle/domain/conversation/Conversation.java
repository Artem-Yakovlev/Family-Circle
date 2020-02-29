package com.tydeya.familycircle.domain.conversation;

import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.description.details.ConversationAttachments;
import com.tydeya.familycircle.domain.conversation.description.details.ConversationDescription;

import java.util.ArrayList;

public class Conversation {

    private String key;
    private ArrayList<ChatMessage> chatMessages;
    private ConversationDescription description;
    private ConversationAttachments attachments;

    public Conversation(ArrayList<ChatMessage> chatMessages, ConversationDescription description,
                        ConversationAttachments attachments, String key) {
        this.chatMessages = chatMessages;
        this.description = description;
        this.attachments = attachments;
        this.key = key;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public String getKey() {
        return key;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public ConversationDescription getDescription() {
        return description;
    }

    public void setDescription(ConversationDescription description) {
        this.description = description;
    }

    public ConversationAttachments getAttachments() {
        return attachments;
    }

    public void setAttachments(ConversationAttachments attachments) {
        this.attachments = attachments;
    }

    public void addMessage(ChatMessage chatMessage) {
        chatMessages.add(chatMessage);
    }

    public ChatMessage getLastMessage() {
        if (chatMessages.size() == 0) {
            return null;
        }
        return chatMessages.get(chatMessages.size() - 1);
    }

    public int getNumberUnreadMessages() {
        int numberUnreadMessages = 0;
        if (chatMessages.size() != 0) {
            for (ChatMessage chatMessage: chatMessages) {
                if (!chatMessage.isViewed()) {
                    numberUnreadMessages++;
                }
            }
        }
        return numberUnreadMessages;
    }
}
