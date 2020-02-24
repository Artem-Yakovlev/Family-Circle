package com.tydeya.familycircle.family;

import com.tydeya.familycircle.family.contact.FamilyContact;
import com.tydeya.familycircle.family.conversation.FamilyConversation;
import com.tydeya.familycircle.family.description.FamilyDescription;
import com.tydeya.familycircle.family.management.IdentificationCode;
import com.tydeya.familycircle.family.management.Plan;
import com.tydeya.familycircle.family.material.Attachments;
import com.tydeya.familycircle.family.member.OldFamilyMember;

import java.util.ArrayList;

public class Family {
    private IdentificationCode id;
    private FamilyDescription familyDescription;
    private FamilyContact familyContact;
    private Plan familyPlan;
    private ArrayList<OldFamilyMember> oldFamilyMembers;
    private Attachments attachments;
    private ArrayList<FamilyConversation> familyConversations;

    public Family(FamilyDescription familyDescription, FamilyContact familyContact,
                  ArrayList<OldFamilyMember> oldFamilyMembers, ArrayList<FamilyConversation> familyConversations) {
        this.familyDescription = familyDescription;
        this.familyContact = familyContact;
        this.oldFamilyMembers = oldFamilyMembers;
        this.familyConversations = familyConversations;
    }

    public FamilyDescription getFamilyDescription() {
        return familyDescription;
    }

    public void setFamilyDescription(FamilyDescription familyDescription) {
        this.familyDescription = familyDescription;
    }

    public FamilyContact getFamilyContact() {
        return familyContact;
    }

    public void setFamilyContact(FamilyContact familyContact) {
        this.familyContact = familyContact;
    }

    public ArrayList<OldFamilyMember> getOldFamilyMembers() {
        return oldFamilyMembers;
    }

    public void setOldFamilyMembers(ArrayList<OldFamilyMember> oldFamilyMembers) {
        this.oldFamilyMembers = oldFamilyMembers;
    }

    public ArrayList<FamilyConversation> getFamilyConversations() {
        return familyConversations;
    }

    public void setFamilyConversations(ArrayList<FamilyConversation> familyConversations) {
        this.familyConversations = familyConversations;
    }
}
