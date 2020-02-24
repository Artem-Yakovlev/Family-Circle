package com.tydeya.familycircle.domain.familymember.contacts.details;

public class FamilyMemberContacts {

    private String fullPhoneNumber;

    public FamilyMemberContacts(String fullPhoneNumber) {
        this.fullPhoneNumber = fullPhoneNumber;
    }

    public String getFullPhoneNumber() {
        return fullPhoneNumber;
    }

    public void setFullPhoneNumber(String fullPhoneNumber) {
        this.fullPhoneNumber = fullPhoneNumber;
    }
}
