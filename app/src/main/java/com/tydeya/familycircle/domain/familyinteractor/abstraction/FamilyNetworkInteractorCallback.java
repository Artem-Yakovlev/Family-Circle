package com.tydeya.familycircle.domain.familyinteractor.abstraction;

import com.tydeya.familycircle.data.familymember.FamilyMember;

import java.util.ArrayList;

public interface FamilyNetworkInteractorCallback {

    void memberDataFromServerUpdate(ArrayList<FamilyMember> members);
}
