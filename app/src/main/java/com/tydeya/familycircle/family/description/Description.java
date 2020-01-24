package com.tydeya.familycircle.family.description;

import android.net.Uri;

public abstract class Description {
    private String text;
    private Uri photo;

    public Description(String text, Uri photo) {
        this.text = text;
        this.photo = photo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Uri getPhoto() {
        return photo;
    }

    public void setPhoto(Uri photo) {
        this.photo = photo;
    }
}
