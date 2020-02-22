package com.tydeya.familycircle.family;

import com.tydeya.domain.contact.FamilyContact;
import com.tydeya.domain.description.FamilyDescription;
import com.tydeya.domain.member.FamilyMember;
import com.tydeya.familycircle.family.conversation.FamilyConversation;

import java.util.ArrayList;

public class Family {
    private long id;
    private FamilyDescription familyDescription;
    private FamilyContact familyContact;
    private ArrayList<FamilyMember> familyMembers;
    private ArrayList<FamilyConversation> familyConversations;

    public Family(FamilyDescription familyDescription, FamilyContact familyContact,
                  ArrayList<FamilyMember> familyMembers, ArrayList<FamilyConversation> familyConversations) {
        this.familyDescription = familyDescription;
        this.familyContact = familyContact;
        this.familyMembers = familyMembers;
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

    public ArrayList<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }

    public void setFamilyMembers(ArrayList<FamilyMember> familyMembers) {
        this.familyMembers = familyMembers;
    }

    public ArrayList<FamilyConversation> getFamilyConversations() {
        return familyConversations;
    }

    public void setFamilyConversations(ArrayList<FamilyConversation> familyConversations) {
        this.familyConversations = familyConversations;
    }
}
