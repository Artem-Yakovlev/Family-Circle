package com.tydeya.data.user;

import com.tydeya.data.family.FamilyMembersRepository;
import com.tydeya.data.messages.ConversationsRepository;
import com.tydeya.domain.member.ActiveMember;

public class FamilyCircle {
    private ActiveMember user;
    private ConversationsRepository conversationsRepository;
    private FamilyMembersRepository familyMembersRepository;

    public FamilyCircle(ActiveMember user, ConversationsRepository conversationsRepository, FamilyMembersRepository familyMembersRepository) {
        this.user = user;
        this.conversationsRepository = conversationsRepository;
        this.familyMembersRepository = familyMembersRepository;
    }


}
