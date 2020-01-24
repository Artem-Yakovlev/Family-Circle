package com.tydeya.familycircle.family;

import com.tydeya.familycircle.family.contact.FamilyContact;
import com.tydeya.familycircle.family.description.FamilyDescription;
import com.tydeya.familycircle.family.management.IdentificationCode;
import com.tydeya.familycircle.family.management.Plan;
import com.tydeya.familycircle.family.material.Attachments;
import com.tydeya.familycircle.family.member.FamilyMember;

import java.util.ArrayList;

public class Family {
    private IdentificationCode id;
    private FamilyDescription familyDescription;
    private FamilyContact familyContact;
    private Plan familyPlan;
    private ArrayList<FamilyMember> familyMembers;
    private Attachments attachments;
}
