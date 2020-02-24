package com.tydeya.familycircle.domain.chatmessage;

public class ChatMessage {

    private String authorPhoneNumber;
    private String text;
    private String dateTime;

    public ChatMessage(String authorPhoneNumber, String text, String dateTime) {
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
