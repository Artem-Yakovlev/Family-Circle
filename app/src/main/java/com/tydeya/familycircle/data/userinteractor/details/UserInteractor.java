package com.tydeya.familycircle.data.userinteractor.details;

import android.content.SharedPreferences;

import com.tydeya.familycircle.domain.constants.User;
import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familymember.contacts.details.FamilyMemberContacts;
import com.tydeya.familycircle.domain.familymember.description.details.FamilyMemberDescription;

//TODO remove MOCK creating
public class UserInteractor {

    private FamilyMember userAccountFamilyMember;

    public UserInteractor(SharedPreferences sharedPreferences) {
        createUserAccountFamilyMember(sharedPreferences);
    }

    public FamilyMember getUserAccountFamilyMember() {
        return userAccountFamilyMember;
    }

    private void createUserAccountFamilyMember(SharedPreferences sharedPreferences) {

        String name = sharedPreferences.getString(User.USER_SHARED_PREFERENCES_NAME, "Artem Yakovlev");
        FamilyMemberDescription description = new FamilyMemberDescription(name, null, null);

        String fullPhoneNumber = sharedPreferences
                .getString(User.USER_SHARED_PREFERENCES_PHONE_NUMBER, "+79053333333");

        FamilyMemberContacts contacts = new FamilyMemberContacts(fullPhoneNumber);

        this.userAccountFamilyMember = new FamilyMember(description, contacts);
    }
}
