package com.tydeya.familycircle.user;

import com.tydeya.familycircle.family.Family;
import com.tydeya.familycircle.family.member.ActiveMember;
import com.tydeya.familycircle.family.member.ActiveMemberBuilder;
import com.tydeya.familycircle.family.member.FamilyMember;

import java.util.ArrayList;

public class User {

    private ActiveMember familyMember;
    private int actualFamily = 0;
    private ArrayList<Family> families;
    private Settings userSettings;
    private static volatile User instance;

    private User() {
        Family testFamily = new Family();

        String[] names = new String[]{"Сергей Петрович", "Мариша", "Джасик", "Петька",
                "Николай", "Гоша", "Зина", "Вика Евдотьевна"};
        testFamily.familyMembers = new ArrayList<>();

        for (String name : names) {
            ActiveMemberBuilder activeMemberBuilder = new ActiveMemberBuilder();
            activeMemberBuilder.setName(name);
            FamilyMember familyMember = activeMemberBuilder.getResult();
            testFamily.familyMembers.add(familyMember);
        }
        families = new ArrayList<>();
        families.add(testFamily);
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

    public Family getFamily() {
        return families.get(actualFamily);
    }
}
