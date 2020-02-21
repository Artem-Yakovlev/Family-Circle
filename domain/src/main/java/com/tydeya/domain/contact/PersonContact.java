package com.tydeya.domain.contact;

public class PersonContact extends Contact {
    private String phoneNumber;

    public PersonContact(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
