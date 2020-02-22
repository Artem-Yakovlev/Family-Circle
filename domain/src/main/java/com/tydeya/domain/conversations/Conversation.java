package com.tydeya.domain.conversations;

import com.tydeya.domain.member.ActiveMember;
import com.tydeya.domain.messages.ChatMessage;

import java.util.ArrayList;

public class Conversation {
    private String name;
    private ArrayList<ChatMessage> messages;
    private String imageAddress;
    private ArrayList<ActiveMember> members;

    public Conversation(String name, ArrayList<ChatMessage> messages, String imageAddress, ArrayList<ActiveMember> members) {
        this.name = name;
        this.messages = messages;
        this.imageAddress = imageAddress;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<ChatMessage> messages) {
        this.messages = messages;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public ArrayList<ActiveMember> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<ActiveMember> members) {
        this.members = members;
    }
}
