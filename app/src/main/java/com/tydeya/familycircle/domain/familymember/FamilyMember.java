package com.tydeya.familycircle.domain.familymember;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tydeya.familycircle.domain.familymember.contacts.FamilyMemberContacts;
import com.tydeya.familycircle.domain.familymember.description.FamilyMemberDescription;

@Entity(tableName = "family_members")
public class FamilyMember {

    @PrimaryKey
    @ColumnInfo(name = "full_phone_number")
    @NonNull
    private String fullPhoneNumber;

    @Embedded
    private FamilyMemberDescription description;

    @Embedded
    private FamilyMemberContacts contacts;


    public FamilyMember(String fullPhoneNumber, FamilyMemberDescription description, FamilyMemberContacts contacts) {
        this.fullPhoneNumber = fullPhoneNumber;
        this.description = description;
        this.contacts = contacts;
    }

    public String getFullPhoneNumber() {
        return fullPhoneNumber;
    }

    public void setFullPhoneNumber(String fullPhoneNumber) {
        this.fullPhoneNumber = fullPhoneNumber;
    }

    public FamilyMemberDescription getDescription() {
        return description;
    }

    public void setDescription(FamilyMemberDescription description) {
        this.description = description;
    }

    public FamilyMemberContacts getContacts() {
        return contacts;
    }

    public void setContacts(FamilyMemberContacts contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "fullPhoneNumber='" + fullPhoneNumber + '\'' +
                ", description=" + description +
                ", contacts=" + contacts +
                '}';
    }
}
