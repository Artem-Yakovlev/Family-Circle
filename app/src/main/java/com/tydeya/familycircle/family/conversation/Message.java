package com.tydeya.familycircle.family.conversation;

import com.tydeya.familycircle.family.member.ActiveMember;

import java.util.Calendar;

public class Message {
    private Calendar time;
    private String text;
    private ActiveMember author;

    public Message(Calendar time, String text, ActiveMember author) {
        this.time = time;
        this.text = text;
        this.author = author;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ActiveMember getAuthor() {
        return author;
    }

    public void setAuthor(ActiveMember author) {
        this.author = author;
    }
}
