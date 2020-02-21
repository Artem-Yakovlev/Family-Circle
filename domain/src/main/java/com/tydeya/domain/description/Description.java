package com.tydeya.domain.description;

public abstract class Description {
    private String text;
    private String photoAddress;

    public Description(String text, String photoAddress) {
        this.text = text;
        this.photoAddress = photoAddress;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }
}
