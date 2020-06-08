package com.tydeya.familycircle.domain.component;

import com.tydeya.familycircle.domain.cooperationorganizer.inject.CooperationModule;
import com.tydeya.familycircle.domain.oldfamilyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.domain.oldfamilyinteractor.injection.FamilyInteractorModule;
import com.tydeya.familycircle.domain.taskorganizer.inject.TasksOrganizerModule;
import com.tydeya.familycircle.domain.taskorganizer.interactor.details.TasksOrganizerInteractor;
import com.tydeya.familycircle.presentation.MainActivity;
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.InConversationFragment;
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog.ConversationAddMemberDialog;
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog.ConversationInfoDialog;
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview.InConversationChatRecyclerViewAdapter;
import com.tydeya.familycircle.presentation.ui.conversationpart.main.ConversationsPage;
import com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.CreateConversationDialog;
import com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview.ConversationViewHolder;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.CreateTaskDialog;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.EditTasksTextDialog;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.TasksByUserFragment;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksbyuser.recyclerview.TasksByUserRecyclerViewAdapter;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksforuser.TasksForUserFragment;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.tasksforuser.recyclerview.TasksForUserRecyclerViewAdapter;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.taskshistory.TasksHistoryFragment;
import com.tydeya.familycircle.presentation.ui.deliverypart.taskorganizer.pages.taskshistory.recyclerview.HistoryTasksRecyclerViewAdapter;
import com.tydeya.familycircle.presentation.ui.livepart.main.details.cooperationrecyclerview.CooperationRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FamilyInteractorModule.class, TasksOrganizerModule.class, CooperationModule.class})
public interface AppComponent {

    void injectFragment(TasksForUserFragment tasksForUserFragment);

    void injectFragment(TasksHistoryFragment tasksHistoryFragment);

    void injectFragment(TasksByUserFragment tasksByUserFragment);

    void injectRecyclerViewAdapter(HistoryTasksRecyclerViewAdapter historyTasksRecyclerViewAdapter);

    void injectRecyclerViewAdapter(TasksForUserRecyclerViewAdapter tasksForUserRecyclerViewAdapter);

    void injectRecyclerViewAdapter(TasksByUserRecyclerViewAdapter tasksByUserRecyclerViewAdapter);

    void injectActivity(MainActivity mainActivity);

    void injectDialog(CreateTaskDialog createTaskDialog);

    void injectDialog(EditTasksTextDialog editTasksTextDialog);

    FamilyInteractor getFamilyInteractor();

    void injectInteractor(@NotNull TasksOrganizerInteractor tasksOrganizerInteractor);

    void injectRecyclerViewAdapter(@NotNull CooperationRecyclerViewAdapter cooperationRecyclerViewAdapter);
}
