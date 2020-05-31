package com.tydeya.familycircle.domain.familyassistant.details;

import com.tydeya.familycircle.data.family.OldFamily;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familyassistant.abstraction.FamilyAssistant;

public class FamilyAssistantImpl implements FamilyAssistant {

    private OldFamily oldFamily;

    public FamilyAssistantImpl(OldFamily oldFamily) {
        this.oldFamily = oldFamily;
    }

    @Override
    public FamilyMember getUserByPhone(String fullPhoneNumber) {
        for (FamilyMember familyMember : oldFamily.getFamilyMembers()) {
            if (familyMember.getFullPhoneNumber().equals(fullPhoneNumber)) {
                return familyMember;
            }
        }
        return null;
    }
}
