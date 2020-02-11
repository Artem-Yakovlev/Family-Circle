package com.tydeya.familycircle.family.member;

import android.net.Uri;

import com.tydeya.familycircle.family.contact.PersonContact;
import com.tydeya.familycircle.family.description.PersonDescription;

import java.util.Calendar;

public abstract class FamilyMember {
    private String name;
    private PersonContact contact;
    private PersonDescription description;

    FamilyMember(String name){
        this.name = name;
    }

    FamilyMember(String name, Calendar birthDate, Uri imageUri, String descriptionText, String phoneNumber){
        this.name = name;
        this.contact = new PersonContact(phoneNumber);
        this.description = new PersonDescription(descriptionText, imageUri, birthDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonContact getContact() {
        return contact;
    }

    public void setContact(PersonContact contact) {
        this.contact = contact;
    }

    public PersonDescription getDescription() {
        return description;
    }

    public void setDescription(PersonDescription description) {
        this.description = description;
    }
}
