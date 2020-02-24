package com.tydeya.familycircle.domain.familymember;

import com.tydeya.familycircle.domain.familymember.contacts.details.FamilyMemberContacts;
import com.tydeya.familycircle.domain.familymember.description.details.FamilyMemberDescription;

public class FamilyMember {

    private FamilyMemberDescription description;
    private FamilyMemberContacts contacts;

    public FamilyMember(FamilyMemberDescription description, FamilyMemberContacts contacts) {
        this.description = description;
        this.contacts = contacts;
    }

    public FamilyMemberDescription getDescription() {
        return description;
    }

    public FamilyMemberContacts getContacts() {
        return contacts;
    }

    public String getFullPhoneNumber() {
        return contacts.getFullPhoneNumber();
    }
}
