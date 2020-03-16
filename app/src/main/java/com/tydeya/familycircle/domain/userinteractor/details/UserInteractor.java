package com.tydeya.familycircle.domain.userinteractor.details;

import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.data.constants.User;
import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.data.familymember.contacts.FamilyMemberContacts;
import com.tydeya.familycircle.data.familymember.description.FamilyMemberDescription;
import com.tydeya.familycircle.data.familymember.otherdata.FamilyMemberCareerData;

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
        long birthDate = sharedPreferences.getLong(User.USER_SHARED_PREFERENCES_BIRTH_DATE, -1);

        FamilyMemberDescription description = new FamilyMemberDescription(name, birthDate, null);

        String fullPhoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        FamilyMemberContacts contacts = new FamilyMemberContacts();
        FamilyMemberCareerData careerData = new FamilyMemberCareerData(null, null);

        this.userAccountFamilyMember = new FamilyMember(fullPhoneNumber, description, contacts, careerData);
    }
}
