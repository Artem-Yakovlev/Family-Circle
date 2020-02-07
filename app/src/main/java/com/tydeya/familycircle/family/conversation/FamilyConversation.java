package com.tydeya.familycircle.family.conversation;

import com.tydeya.familycircle.family.member.ActiveMember;

import java.util.ArrayList;

public class FamilyConversation extends Conversation {

    private ArrayList<ActiveMember> members;

    public FamilyConversation(ArrayList<Message> messages, String name) {
        super(messages, name);
    }

    public ArrayList<ActiveMember> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<ActiveMember> members) {
        this.members = members;
    }
}
