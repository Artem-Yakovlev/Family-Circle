package com.tydeya.domain.member;


import java.util.Calendar;

public class ActiveMember extends FamilyMember {

    private long id;

    public ActiveMember(String name){
        super(name);
    }

    ActiveMember(String name, Calendar birthDate, String imageAddress,
                 String descriptionText, String phoneNumber) {
        super(name, birthDate, imageAddress, descriptionText, phoneNumber);
    }

    public static class Builder {

        private String name;
        private Calendar birthDate;
        private String imageAddress;
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

        public void setImageAddress(String imageAddress) {
            this.imageAddress = imageAddress;
        }

        public void setDescriptionText(String descriptionText) {
            this.descriptionText = descriptionText;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public ActiveMember build() {
            return new ActiveMember(name, birthDate, imageAddress, descriptionText, phoneNumber);
        }
    }

}
