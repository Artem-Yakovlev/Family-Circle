package com.tydeya.familycircle.data.familyinteractor.abstraction;

import com.tydeya.familycircle.domain.familymember.FamilyMember;

import java.util.ArrayList;

public interface FamilyNetworkInteractorCallback {

    void memberDataFromServerUpdate(ArrayList<FamilyMember> members);
}
