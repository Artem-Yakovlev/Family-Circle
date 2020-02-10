package com.tydeya.familycircle.user;

import com.tydeya.familycircle.family.Family;
import com.tydeya.familycircle.family.conversation.FamilyConversation;
import com.tydeya.familycircle.family.conversation.messages.Message;
import com.tydeya.familycircle.family.conversation.messages.PersonMessage;
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


        ArrayList<Message> messages = new ArrayList<>();
        messages.add(new PersonMessage(null, "Hello", new ActiveMember("Ирина Яковлева")));
        messages.add(new PersonMessage(null, "What are you thinking about today weather?", new ActiveMember("Елена Яковлева")));
        messages.add(new PersonMessage(null, "Nothing...", new ActiveMember("Елена Яковлева")));
        messages.add(new PersonMessage(null, ":)", new ActiveMember("Артем Яковлев")));
        messages.add(new PersonMessage(null, "It's very strange monologue!", userFamilyMember));
        FamilyConversation conversation1 = new FamilyConversation(messages, "Main conf");
        FamilyConversation conversation2 = new FamilyConversation(messages, "Second conf");
        ArrayList<FamilyConversation> conversations = new ArrayList<>();
        conversations.add(conversation1);
        conversations.add(conversation2);


        Family testFamily = new Family(testDescription, null, familyMembers, conversations);
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
