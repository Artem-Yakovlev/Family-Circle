package com.tydeya.familycircle.family.member;

import android.net.Uri;

import com.tydeya.familycircle.family.management.AccessLevel;
import com.tydeya.familycircle.family.management.IdentificationCode;
import com.tydeya.familycircle.family.management.Plan;

import java.util.Calendar;

public class ActiveMemberOld extends OldFamilyMember {

    private IdentificationCode id;
    private AccessLevel accessLevel;
    private Plan plan;

    public ActiveMemberOld(String name){
        super(name);
    }

    ActiveMemberOld(String name, Calendar birthDate, Uri imageUri,
                    String descriptionText, String phoneNumber) {
        super(name, birthDate, imageUri, descriptionText, phoneNumber);
    }

    public static class Builder {

        private String name;
        private Calendar birthDate;
        private Uri imageUri;
        private String descriptionText;
        private String phoneNumber;

        public Builder() {

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

        public ActiveMemberOld build() {
            return new ActiveMemberOld(name, birthDate, imageUri, descriptionText, phoneNumber);
        }
    }

}
