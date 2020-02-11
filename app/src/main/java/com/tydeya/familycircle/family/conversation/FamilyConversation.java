package com.tydeya.familycircle.family.conversation;

import android.os.Parcel;
import android.os.Parcelable;

import com.tydeya.familycircle.family.conversation.messages.Message;
import com.tydeya.familycircle.family.member.ActiveMember;

import java.util.ArrayList;

public class FamilyConversation extends Conversation implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
