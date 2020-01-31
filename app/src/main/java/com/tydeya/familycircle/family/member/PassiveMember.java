package com.tydeya.familycircle.family.member;

import android.net.Uri;

import java.util.Calendar;

public class PassiveMember extends FamilyMember {

    public PassiveMember(String name) {
        super(name);
    }

    public PassiveMember(String name, Calendar birthDate, Uri imageUri, String descriptionText, String phoneNumber) {
        super(name, birthDate, imageUri, descriptionText, phoneNumber);
    }


}
