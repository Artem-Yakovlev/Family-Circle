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

    ActiveMember(String name, Calendar birthDate, Uri imageUri, String descriptionText, String phoneNumber) {
        super(name, birthDate, imageUri, descriptionText, phoneNumber);
    }
}
