package com.tydeya.domain.messages;

import java.util.Calendar;

public class ChatMessage {
    private Calendar time;
    private String text;
    private ChatMessageType type;
    private long authorId;

    private ChatMessage(Calendar time, String text, ChatMessageType type, long authorId) {
        this.time = time;
        this.text = text;
        this.type = type;
        this.authorId = authorId;
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

    public ChatMessageType getType() {
        return type;
    }

    public void setType(ChatMessageType type) {
        this.type = type;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
}
