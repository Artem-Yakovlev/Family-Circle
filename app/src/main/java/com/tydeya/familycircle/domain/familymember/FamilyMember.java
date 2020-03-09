package com.tydeya.familycircle.domain.familymember;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tydeya.familycircle.domain.familymember.contacts.FamilyMemberContacts;
import com.tydeya.familycircle.domain.familymember.description.FamilyMemberDescription;
import com.tydeya.familycircle.domain.familymember.otherdata.FamilyMemberCareerData;

import org.jetbrains.annotations.NotNull;

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

    @Embedded
    private FamilyMemberCareerData careerData;

    public FamilyMember(@NonNull String fullPhoneNumber, FamilyMemberDescription description,
                        FamilyMemberContacts contacts, FamilyMemberCareerData careerData) {
        this.fullPhoneNumber = fullPhoneNumber;
        this.description = description;
        this.contacts = contacts;
        this.careerData = careerData;
    }

    @NonNull
    public String getFullPhoneNumber() {
        return fullPhoneNumber;
    }

    public void setFullPhoneNumber(@NonNull String fullPhoneNumber) {
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

    public FamilyMemberCareerData getCareerData() {
        return careerData;
    }

    public void setCareerData(FamilyMemberCareerData careerData) {
        this.careerData = careerData;
    }

    @NotNull
    @Override
    public String toString() {
        return "FamilyMember{" +
                "fullPhoneNumber='" + fullPhoneNumber + '\'' +
                ", description=" + description +
                ", contacts=" + contacts +
                ", otherData=" + careerData +
                '}';
    }
}
