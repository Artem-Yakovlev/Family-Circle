package com.tydeya.familycircle.data.familyassistant.abstraction;

import com.tydeya.familycircle.domain.familymember.FamilyMember;

public interface FamilyAssistant {

    FamilyMember getUserByPhone(String fullPhoneNumber);

}
