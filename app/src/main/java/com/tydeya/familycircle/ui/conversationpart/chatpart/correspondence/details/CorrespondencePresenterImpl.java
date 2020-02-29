package com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details;

import com.google.firebase.auth.FirebaseAuth;
import com.tydeya.familycircle.App;
import com.tydeya.familycircle.data.conversationsassistant.details.ConversationsAssistantImpl;
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.data.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.data.userinteractor.details.UserInteractor;
import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.Conversation;
import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.ui.conversationpart.chatpart.MessagingActivity;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.abstraction.CorrespondencePresenter;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.abstraction.CorrespondenceView;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

public class CorrespondencePresenterImpl implements CorrespondencePresenter {

    @Inject
    ConversationInteractor conversationInteractor;

    @Inject
    UserInteractor userInteractor;

    @Inject
    FamilyInteractor familyInteractor;

    private CorrespondenceView view;

    CorrespondencePresenterImpl(CorrespondenceView view) {
        App.getComponent().injectPresenter(this);
        this.view = view;
    }

    @Override
    public void onClickSendButton(String input) {
        if (!input.trim().equals("")) {
            sendMessage(input);
            view.messageSent();
        }
    }

    private void sendMessage(String messageText) {
        String userPhoneNumber = userInteractor.getUserAccountFamilyMember().getFullPhoneNumber();
        ChatMessage chatMessage = new ChatMessage(userPhoneNumber, messageText, new Date(), true);
        Conversation conversation = new ConversationsAssistantImpl()
                .getConversationByKey(MessagingActivity.correspondenceKey);

        ArrayList<String> phoneNumbers = new ArrayList<>();
        for (FamilyMember familyMember : familyInteractor.getActualFamily().getFamilyMembers()) {
            if (!familyMember.getFullPhoneNumber()
                    .equals(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())) {

                phoneNumbers.add(familyMember.getFullPhoneNumber());
            }
        }

        conversation.addMessage(chatMessage);
        conversationInteractor.sendMessage(chatMessage, conversation, phoneNumbers);
    }

    @Override
    public void readAllMessages() {
        Conversation conversation = new ConversationsAssistantImpl()
                .getConversationByKey(MessagingActivity.correspondenceKey);

        if (conversation.getNumberUnreadMessages() != 0) {
            conversationInteractor.readMessages(conversation);
        }
    }
}
