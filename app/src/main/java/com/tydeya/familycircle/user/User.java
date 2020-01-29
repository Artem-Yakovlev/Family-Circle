package com.tydeya.familycircle.user;

import com.tydeya.familycircle.family.Family;
import com.tydeya.familycircle.family.member.ActiveMember;

import java.util.ArrayList;

public class User {

    private ActiveMember familyMember;
    private ArrayList<Family> families;
    private Settings userSettings;
    private static volatile User instance;

    private User(){

    }

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public ActiveMember getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(ActiveMember familyMember) {
        this.familyMember = familyMember;
    }
}
