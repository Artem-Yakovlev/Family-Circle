package com.tydeya.familycircle.domain.component;

import com.tydeya.familycircle.domain.conversationsassistant.details.ConversationsAssistantImpl;
import com.tydeya.familycircle.domain.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.domain.conversationsinteractor.injection.ConversationInteractorModule;
import com.tydeya.familycircle.domain.eventreminder.interactor.injection.EventInteractorModule;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.domain.familyinteractor.injection.FamilyInteractorModule;
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.injection.KitchenOrganizerModule;
import com.tydeya.familycircle.domain.userinteractor.details.UserInteractor;
import com.tydeya.familycircle.domain.userinteractor.injection.UserInteractorModule;
import com.tydeya.familycircle.ui.MainActivity;
import com.tydeya.familycircle.ui.conversationpart.MainConversationPage;
import com.tydeya.familycircle.ui.conversationpart.recyclerview.MainConversationRecyclerViewAdapter;
import com.tydeya.familycircle.ui.conversationpart.chatpart.MessagingActivity;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details.ChatRecyclerViewAdapter;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details.CorrespondenceFragment;
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details.CorrespondencePresenterImpl;
import com.tydeya.familycircle.ui.livepart.main.details.MainLivePage;
import com.tydeya.familycircle.ui.livepart.main.details.recyclerview.FamilyMembersStoriesRecyclerViewAdapter;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.details.MemberPersonFragment;
import com.tydeya.familycircle.ui.planpart.eventreminder.EventReminderFragment;
import com.tydeya.familycircle.ui.planpart.eventreminder.eventviewpage.EventViewFragment;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.CreateBuyListDialog;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.alllists.FoodForBuyFragment;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.BuyCatalogFragment;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.BuyCatalogSettingsDialog;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.CreateNewProductDialog;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodforbuy.buylist.EditProductDataDialog;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.DeleteFoodInFridgeDialog;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.FoodInFridgeFragment;
import com.tydeya.familycircle.ui.planpart.kitchenorganizer.pages.foodinfridge.FridgeAddFoodDialog;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FamilyInteractorModule.class, ConversationInteractorModule.class,
        UserInteractorModule.class, KitchenOrganizerModule.class, EventInteractorModule.class})
public interface AppComponent {

    void injectFragment(MainLivePage mainLivePage);

    void injectFragment(MemberPersonFragment memberPersonFragment);

    void injectFragment(MainConversationPage mainConversationPage);

    void injectFragment(CorrespondenceFragment correspondenceFragment);

    void injectFragment(EventViewFragment eventViewFragment);

    void injectRecyclerViewAdapter(ChatRecyclerViewAdapter chatRecyclerViewAdapter);

    void injectRecyclerViewAdapter(MainConversationRecyclerViewAdapter adapter);

    void injectRecyclerViewAdapter(FamilyMembersStoriesRecyclerViewAdapter adapter);

    void injectPresenter(CorrespondencePresenterImpl correspondencePresenterImpl);

    void injectActivity(MessagingActivity messagingActivity);

    void injectActivity(MainActivity mainActivity);

    void injectConversationAssistant(ConversationsAssistantImpl conversationsAssistant);

    void injectFoodForBuyFragment(FoodForBuyFragment foodForBuyFragment);

    void injectFoodInFridgeFragment(FoodInFridgeFragment foodInFridgeFragment);

    void injectBuyCatalogFragment(BuyCatalogFragment buyCatalogFragment);

    void injectDialog(CreateBuyListDialog createBuyListDialog);

    void injectDialog(CreateNewProductDialog createNewProductDialog);

    void injectDialog(BuyCatalogSettingsDialog buyCatalogSettingsDialog);

    void injectDialog(EditProductDataDialog editProductDataDialog);

    void injectDialog(DeleteFoodInFridgeDialog deleteFoodInFridgeDialog);

    void injectDialog(FridgeAddFoodDialog fridgeAddFoodDialog);

    void injectEventReminderFragment(EventReminderFragment eventReminderFragment);

    ConversationInteractor getConversationInteractor();

    UserInteractor getUserInteractor();

    FamilyInteractor getFamilyInteractor();
}
