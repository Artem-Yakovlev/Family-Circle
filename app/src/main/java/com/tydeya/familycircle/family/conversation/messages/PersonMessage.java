package com.tydeya.familycircle.family.conversation.messages;

import android.net.Uri;

import com.tydeya.familycircle.family.member.ActiveMemberOld;

import java.util.Calendar;

public class PersonMessage extends Message {

    private ActiveMemberOld author;
    private Uri imageUri;

    public PersonMessage(Calendar time, String text, ActiveMemberOld author) {
        super(time, text);
        this.author = author;
    }

    public ActiveMemberOld getAuthor() {
        return author;
    }

    public void setAuthor(ActiveMemberOld author) {
        this.author = author;
    }
}
