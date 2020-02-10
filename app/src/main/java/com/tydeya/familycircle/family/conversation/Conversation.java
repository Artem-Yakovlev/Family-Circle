package com.tydeya.familycircle.family.conversation;

import android.net.Uri;

import com.tydeya.familycircle.family.conversation.messages.Message;

import java.util.ArrayList;

public abstract class Conversation {
    private ArrayList<Message> messages;
    private String name;
    private Uri imageUri;

    public Conversation(ArrayList<Message> messages, String name) {
        this.messages = messages;
        this.name = name;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
