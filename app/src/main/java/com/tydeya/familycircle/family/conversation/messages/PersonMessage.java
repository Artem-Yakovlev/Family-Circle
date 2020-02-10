package com.tydeya.familycircle.family.conversation.messages;

import android.net.Uri;

import com.tydeya.familycircle.family.member.ActiveMember;

import java.util.Calendar;

public class PersonMessage extends Message {

    private ActiveMember author;
    private Uri imageUri;

    public PersonMessage(Calendar time, String text, ActiveMember author) {
        super(time, text);
        this.author = author;
    }

    public ActiveMember getAuthor() {
        return author;
    }

    public void setAuthor(ActiveMember author) {
        this.author = author;
    }
}
