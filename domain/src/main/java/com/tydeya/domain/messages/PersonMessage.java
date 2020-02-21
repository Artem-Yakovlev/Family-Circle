package com.tydeya.domain.messages;


import java.util.Calendar;

public class PersonMessage extends Message {

    private int authorId;
    private String imageAddress;

    public PersonMessage(Calendar time, String text, int authorId) {
        super(time, text);
        this.authorId = authorId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthor(int authorId) {
        this.authorId = authorId;
    }
}
