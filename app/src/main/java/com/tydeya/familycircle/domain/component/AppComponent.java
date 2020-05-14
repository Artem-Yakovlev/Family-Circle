package com.tydeya.familycircle.domain.component;

import com.tydeya.familycircle.domain.cooperationorganizer.inject.CooperationModule;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.domain.familyinteractor.injection.FamilyInteractorModule;
import com.tydeya.familycircle.domain.messenger.conversationlistener.ConversationListener;
import com.tydeya.familycircle.domain.messenger.inject.MessengerModule;
import com.tydeya.familycircle.domain.messenger.networkinteractor.details.MessengerNetworkInteractorImpl;
import com.tydeya.familycircle.domain.onlinemanager.injection.OnlineManagerModule;
import com.tydeya.familycircle.domain.taskorganizer.inject.TasksOrganizerModule;
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor;
import com.tydeya.familycircle.domain.taskorganizer.networkinteractor.details.TasksOrganizerNetworkInteractorImpl;
import com.tydeya.familycircle.framework.editaccount.details.EditAccountToolImpl;
import com.tydeya.familycircle.ui.MainActivity;
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.InConversationFragment;
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog.ConversationAddMemberDialog;
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog.ConversationInfoDialog;
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.recyclerview.InConversationChatRecyclerViewAdapter;
import com.tydeya.familycircle.ui.conversationpart.main.MainConversationPage;
import com.tydeya.familycircle.ui.conversationpart.main.createconversation.CreateConversationDialog;
import com.tydeya.familycircle.ui.conversationpart.main.recyclerview.MainConversationViewHolder;
import com.tydeya.familycircle.ui.livepart.main.details.MainLiveFragment;
import com.tydeya.familycircle.ui.livepart.main.details.cooperationrecyclerview.CooperationRecyclerViewAdapter;
import com.tydeya.familycircle.ui.livepart.main.details.storiesrecyclerview.FamilyMembersStoriesRecyclerViewAdapter;
import com.tydeya.familycircle.ui.livepart.memberpersonpage.details.MemberPersonFragment;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksbyuser.CreateTaskDialog;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksbyuser.EditTasksTextDialog;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksbyuser.TasksByUserFragment;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksbyuser.recyclerview.TasksByUserRecyclerViewAdapter;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksforuser.TasksForUserFragment;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.tasksforuser.recyclerview.TasksForUserRecyclerViewAdapter;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.taskshistory.TasksHistoryFragment;
import com.tydeya.familycircle.ui.planpart.taskorganizer.pages.taskshistory.recyclerview.HistoryTasksRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FamilyInteractorModule.class, TasksOrganizerModule.class,
        MessengerModule.class, OnlineManagerModule.class, CooperationModule.class})
public interface AppComponent {

    void injectFragment(MainLiveFragment mainLiveFragment);

    void injectFragment(MemberPersonFragment memberPersonFragment);

    void injectFragment(MainConversationPage mainConversationPage);

    void injectFragment(InConversationFragment inConversationFragment);

    void injectFragment(TasksForUserFragment tasksForUserFragment);

    void injectFragment(TasksHistoryFragment tasksHistoryFragment);

    void injectFragment(TasksByUserFragment tasksByUserFragment);

    void injectRecyclerViewAdapter(InConversationChatRecyclerViewAdapter inConversationChatRecyclerViewAdapter);

    void injectRecyclerViewAdapter(HistoryTasksRecyclerViewAdapter historyTasksRecyclerViewAdapter);

    void injectRecyclerViewAdapter(TasksForUserRecyclerViewAdapter tasksForUserRecyclerViewAdapter);

    void injectRecyclerViewAdapter(TasksByUserRecyclerViewAdapter tasksByUserRecyclerViewAdapter);

    void injectRecyclerViewAdapter(FamilyMembersStoriesRecyclerViewAdapter adapter);

    void injectActivity(MainActivity mainActivity);

    void injectViewHolder(MainConversationViewHolder mainConversationViewHolder);

    void injectDialog(ConversationInfoDialog conversationInfoDialog);

    void injectDialog(CreateTaskDialog createTaskDialog);

    void injectDialog(CreateConversationDialog createConversationDialog);

    void injectDialog(EditTasksTextDialog editTasksTextDialog);

    void injectDialog(ConversationAddMemberDialog conversationAddMemberDialog);

    void injectInteractor(MessengerNetworkInteractorImpl messengerNetworkInteractorImpl);

    FamilyInteractor getFamilyInteractor();

    void injectInteractor(ConversationListener conversationListener);

    void injectInteractor(TasksOrganizerNetworkInteractorImpl tasksOrganizerNetworkInteractorImpl);

    void injectTool(@NotNull EditAccountToolImpl editAccountToolImpl);

    void injectInteractor(@NotNull TasksOrganizerInteractor tasksOrganizerInteractor);

    void injectRecyclerViewAdapter(@NotNull CooperationRecyclerViewAdapter cooperationRecyclerViewAdapter);
}
