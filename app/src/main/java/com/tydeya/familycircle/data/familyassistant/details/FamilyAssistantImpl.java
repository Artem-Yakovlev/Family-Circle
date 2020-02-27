package com.tydeya.familycircle.data.familyassistant.details;

import com.tydeya.familycircle.data.familyassistant.abstraction.FamilyAssistant;
import com.tydeya.familycircle.domain.family.Family;
import com.tydeya.familycircle.domain.familymember.FamilyMember;

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
