package com.tydeya.familycircle.domain.familyinteractor.abstraction;

import android.util.ArrayMap;

public interface FamilyNetworkInteractor {

    void requireMembersDataFromServer();

    ArrayMap<String, Boolean> requireUsersAreOnlineData();
}
