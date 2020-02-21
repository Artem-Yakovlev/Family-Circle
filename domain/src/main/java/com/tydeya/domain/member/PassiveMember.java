package com.tydeya.domain.member;

import java.util.Calendar;

public class PassiveMember extends FamilyMember {

    public PassiveMember(String name) {
        super(name);
    }

    public PassiveMember(String name, Calendar birthDate, String imageAddress, String descriptionText, String phoneNumber) {
        super(name, birthDate, imageAddress, descriptionText, phoneNumber);
    }


}
