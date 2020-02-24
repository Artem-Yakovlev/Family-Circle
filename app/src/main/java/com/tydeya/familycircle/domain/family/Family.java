package com.tydeya.familycircle.domain.family;

import com.tydeya.familycircle.domain.family.description.FamilyDescription;
import com.tydeya.familycircle.domain.familymember.FamilyMember;

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
}
