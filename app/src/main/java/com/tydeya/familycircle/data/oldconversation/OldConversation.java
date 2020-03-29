package com.tydeya.familycircle.data.oldconversation;

import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.oldconversation.description.details.OldConversationAttachments;
import com.tydeya.familycircle.data.oldconversation.description.details.OldConversationDescription;

import java.util.ArrayList;

public class OldConversation {

    private String key;
    private ArrayList<ChatMessage> chatMessages;
    private OldConversationDescription description;
    private OldConversationAttachments attachments;

    public OldConversation(ArrayList<ChatMessage> chatMessages, OldConversationDescription description,
                           OldConversationAttachments attachments, String key) {
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

    public OldConversationDescription getDescription() {
        return description;
    }

    public void setDescription(OldConversationDescription description) {
        this.description = description;
    }

    public OldConversationAttachments getAttachments() {
        return attachments;
    }

    public void setAttachments(OldConversationAttachments attachments) {
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
