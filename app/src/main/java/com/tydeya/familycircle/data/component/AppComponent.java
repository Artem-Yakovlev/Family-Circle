package com.tydeya.familycircle.data.component;

import com.tydeya.familycircle.data.conversationsinteractor.injection.ConversationInteractorModule;
import com.tydeya.familycircle.data.familyinteractor.injection.FamilyInteractorModule;
import com.tydeya.familycircle.ui.conversationpart.MainConversationPage;
import com.tydeya.familycircle.ui.conversationpart.chatpart.CorrespondenceFragment;
import com.tydeya.familycircle.ui.livepart.main.details.MainLivePage;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.details.FamilyMemberPersonPage;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FamilyInteractorModule.class, ConversationInteractorModule.class})
public interface AppComponent {

    void injectFragment(MainLivePage mainLivePage);

    void injectFragment(FamilyMemberPersonPage familyMemberPersonPage);

    void injectFragment(MainConversationPage mainConversationPage);

    void injectFragment(CorrespondenceFragment correspondenceFragment);
}
