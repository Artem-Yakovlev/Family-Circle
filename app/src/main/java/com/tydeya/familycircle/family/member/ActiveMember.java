package com.tydeya.familycircle.family.member;

import android.net.Uri;

import com.tydeya.familycircle.family.management.AccessLevel;
import com.tydeya.familycircle.family.management.IdentificationCode;
import com.tydeya.familycircle.family.management.Plan;

import java.util.Calendar;

public class ActiveMember extends FamilyMember {

    private IdentificationCode id;
    private AccessLevel accessLevel;
    private Plan plan;

    public ActiveMember(String name){
        super(name);
    }

    ActiveMember(String name, Calendar birthDate, Uri imageUri,
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

        public ActiveMember build() {
            return new ActiveMember(name, birthDate, imageUri, descriptionText, phoneNumber);
        }
    }

}
