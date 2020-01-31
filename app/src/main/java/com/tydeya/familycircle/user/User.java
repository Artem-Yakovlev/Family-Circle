package com.tydeya.familycircle.user;

import com.tydeya.familycircle.family.Family;
import com.tydeya.familycircle.family.description.FamilyDescription;
import com.tydeya.familycircle.family.member.ActiveMember;
import com.tydeya.familycircle.family.member.FamilyMember;
import com.tydeya.familycircle.family.member.PassiveMember;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class User {

    private ActiveMember userFamilyMember;
    private int actualFamily = 0;
    private ArrayList<Family> families;
    private Settings userSettings;

    private static volatile User instance;


    private User() {

        families = new ArrayList<>();
        FamilyDescription testDescription = new FamilyDescription("Это тестовая семья", null);
        ArrayList<FamilyMember> familyMembers = new ArrayList<>();

        ActiveMember.Builder activeMemberBuilder = new ActiveMember.Builder();
        activeMemberBuilder
                .setBirthDate(new GregorianCalendar(2001, Calendar.JANUARY, 28));
        activeMemberBuilder.setName("Артем Яковлев");
        activeMemberBuilder.setDescriptionText("Это любимый я!");
        activeMemberBuilder.setPhoneNumber("+79056644712");
        userFamilyMember = activeMemberBuilder.build();

        familyMembers.add(new ActiveMember("Ирина Яковлева"));
        familyMembers.add(new ActiveMember("Дмитрий Яковлев"));
        familyMembers.add(new ActiveMember("Анастасия Дмитриева"));
        familyMembers.add(new ActiveMember("Елена Яковлева"));
        familyMembers.add(new PassiveMember("Владимир Яковлев"));

        Family testFamily = new Family(testDescription, null, familyMembers);
        families.add(testFamily);
    }

    public static synchronized User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public ActiveMember getUserFamilyMember() {
        return userFamilyMember;
    }

    public void setUserFamilyMember(ActiveMember userFamilyMember) {
        this.userFamilyMember = userFamilyMember;
    }

    public Family getFamily() {
        return families.get(actualFamily);
    }
}
