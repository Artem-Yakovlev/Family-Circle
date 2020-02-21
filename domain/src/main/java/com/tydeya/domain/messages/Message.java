package com.tydeya.domain.messages;

import java.util.Calendar;

public abstract class Message {
    private Calendar time;
    private String text;

    Message(Calendar time, String text) {
        this.time = time;
        this.text = text;
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
}
