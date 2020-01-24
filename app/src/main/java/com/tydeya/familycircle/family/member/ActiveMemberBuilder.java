package com.tydeya.familycircle.family.member;

import android.icu.util.Calendar;
import android.net.Uri;

public class ActiveMemberBuilder {

    private String name;
    private Calendar birthDate;
    private Uri imageUri;
    private String descriptionText;
    private String phoneNumber;

    public ActiveMemberBuilder() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ActiveMember getResult(){
        return new ActiveMember(name, birthDate, imageUri, descriptionText, phoneNumber);
    }
}
