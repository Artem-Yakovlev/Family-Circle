package com.tydeya.familycircle.domain.familyassistant.details;

import com.tydeya.familycircle.data.family.Family;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familyassistant.abstraction.FamilyAssistant;

public class FamilyAssistantImpl implements FamilyAssistant {

    private Family family;

    public FamilyAssistantImpl(Family family) {
        this.family = family;
    }

    @Override
    public FamilyMember getUserByPhone(String fullPhoneNumber) {
        for (FamilyMember familyMember: family.getFamilyMembers()) {
            if (familyMember.getFullPhoneNumber().equals(fullPhoneNumber)) {
                return familyMember;
            }
        }
        return null;
    }
}
