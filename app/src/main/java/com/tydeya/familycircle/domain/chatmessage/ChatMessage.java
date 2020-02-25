package com.tydeya.familycircle.domain.chatmessage;

import java.util.Date;

public class ChatMessage {

    private String authorPhoneNumber;
    private String text;
    private Date dateTime;

    public ChatMessage(String authorPhoneNumber, String text, Date dateTime) {
        this.authorPhoneNumber = authorPhoneNumber;
        this.text = text;
        this.dateTime = dateTime;
    }

    public String getAuthorPhoneNumber() {
        return authorPhoneNumber;
    }

    public void setAuthorPhoneNumber(String authorPhoneNumber) {
        this.authorPhoneNumber = authorPhoneNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
