package com.tydeya.familycircle.data.component;

import com.tydeya.familycircle.data.conversationsassistant.details.ConversationsAssistantImpl;
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.data.conversationsinteractor.injection.ConversationInteractorModule;
import com.tydeya.familycircle.data.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.data.familyinteractor.injection.FamilyInteractorModule;
import com.tydeya.familycircle.data.userinteractor.details.UserInteractor;
import com.tydeya.familycircle.data.userinteractor.injection.UserInteractorModule;
import com.tydeya.familycircle.ui.MainActivity;
import com.tydeya.familycircle.ui.conversationpart.MainConversationPage;
import com.tydeya.familycircle.ui.conversationpart.recyclerview.MainConversationRecyclerViewAdapter;
import com.tydeya.familycircle.ui.conversationpart.chatpart.MessagingActivity;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details.ChatRecyclerViewAdapter;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details.CorrespondenceFragment;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details.CorrespondencePresenterImpl;
import com.tydeya.familycircle.ui.livepart.main.details.MainLivePage;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.details.MemberPersonFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FamilyInteractorModule.class, ConversationInteractorModule.class, UserInteractorModule.class})
public interface AppComponent {

    void injectFragment(MainLivePage mainLivePage);

    void injectFragment(MemberPersonFragment memberPersonFragment);

    void injectFragment(MainConversationPage mainConversationPage);

    void injectFragment(CorrespondenceFragment correspondenceFragment);

    void injectRecyclerViewAdapter(ChatRecyclerViewAdapter chatRecyclerViewAdapter);

    void injectRecyclerViewAdapter(MainConversationRecyclerViewAdapter mainConversationRecyclerViewAdapter);

    void injectPresenter(CorrespondencePresenterImpl correspondencePresenterImpl);

    void injectActivity(MessagingActivity messagingActivity);

    void injectActivity(MainActivity mainActivity);

    void injectConversationAssistant(ConversationsAssistantImpl conversationsAssistant);

    ConversationInteractor getConversationInteractor();

    UserInteractor getUserInteractor();

    FamilyInteractor getFamilyInteractor();
}
