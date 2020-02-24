package com.tydeya.familycircle.family.member;

import android.net.Uri;

import java.util.Calendar;

public class PassiveMemberOld extends OldFamilyMember {

    public PassiveMemberOld(String name) {
        super(name);
    }

    public PassiveMemberOld(String name, Calendar birthDate, Uri imageUri, String descriptionText, String phoneNumber) {
        super(name, birthDate, imageUri, descriptionText, phoneNumber);
    }


}
