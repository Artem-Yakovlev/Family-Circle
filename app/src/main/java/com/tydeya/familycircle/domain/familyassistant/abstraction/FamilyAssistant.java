package com.tydeya.familycircle.domain.familyassistant.abstraction;

import com.tydeya.familycircle.data.familymember.FamilyMember;

public interface FamilyAssistant {

    FamilyMember getUserByPhone(String fullPhoneNumber);

}
