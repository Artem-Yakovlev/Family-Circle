package com.tydeya.familycircle.family.contact;

public class PersonContact extends Contact {
    private String phoneNumber;

    public PersonContact(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
