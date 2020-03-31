package com.tydeya.familycircle.data.family;

import com.tydeya.familycircle.data.family.description.FamilyDescription;
import com.tydeya.familycircle.data.familymember.FamilyMember;

import java.util.ArrayList;

public class Family {
    private final long id;
    private FamilyDescription description;
    private ArrayList<FamilyMember> familyMembers;

    public Family(long id, FamilyDescription description, ArrayList<FamilyMember> familyMembers) {
        this.id = id;
        this.description = description;
        this.familyMembers = familyMembers;
    }

    public long getId() {
        return id;
    }

    public FamilyDescription getDescription() {
        return description;
    }

    public void setDescription(FamilyDescription description) {
        this.description = description;
    }

    public ArrayList<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(ArrayList<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public ArrayList<FamilyMember> getFamilyMemberExceptUserPhone(String userPhone) {
        ArrayList<FamilyMember> familyMemberExceptUser = new ArrayList<>();

        for (FamilyMember familyMember : familyMembers) {
            if (!familyMember.getFullPhoneNumber().equals(userPhone)) {
                familyMemberExceptUser.add(familyMember);
            }
        }
        return familyMemberExceptUser;
    }
}
