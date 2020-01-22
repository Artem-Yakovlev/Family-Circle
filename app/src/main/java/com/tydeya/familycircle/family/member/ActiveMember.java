package com.tydeya.familycircle.family.member;

import com.tydeya.familycircle.family.management.AccessLevel;
import com.tydeya.familycircle.family.management.IdentificationCode;
import com.tydeya.familycircle.family.management.Plan;

public class ActiveMember extends FamilyMember {
    private IdentificationCode id;
    private AccessLevel accessLevel;
    private Plan plan;
}
