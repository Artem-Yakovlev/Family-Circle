package com.tydeya.familycircle.data.familyInteractor.abstraction;

import com.tydeya.familycircle.domain.familymember.FamilyMember;

import java.util.ArrayList;

public interface FamilyNetworkInteractorCallback {

    void memberDataFromServerUpdate(ArrayList<FamilyMember> members);
}
