package com.tydeya.familycircle.family.member;

import android.icu.util.Calendar;
import android.net.Uri;

public class PassiveMember extends FamilyMember {

    PassiveMember(String name, Calendar birthDate, Uri imageUri, String descriptionText, String phoneNumber) {
        super(name, birthDate, imageUri, descriptionText, phoneNumber);
    }
}
