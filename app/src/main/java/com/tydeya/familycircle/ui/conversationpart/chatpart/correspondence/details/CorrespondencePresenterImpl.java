package com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.data.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.data.userinteractor.details.UserInteractor;
import com.tydeya.familycircle.domain.chatmessage.ChatMessage;
import com.tydeya.familycircle.domain.conversation.Conversation;
import com.tydeya.familycircle.ui.conversationpart.chatpart.MessagingActivity;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.abstraction.CorrespondencePresenter;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.abstraction.CorrespondenceView;

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
        ChatMessage chatMessage = new ChatMessage(userPhoneNumber, messageText, null);
        Conversation conversation = conversationInteractor.getConversations().get(MessagingActivity.correspondencePosition);

        conversation.addMessage(chatMessage);
        conversationInteractor.sendMessage(chatMessage, conversation);
    }
}
