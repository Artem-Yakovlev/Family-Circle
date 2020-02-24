package com.tydeya.familycircle.ui.conversationpart;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.conversationsinteractor.abstraction.ConversationInteractorCallback;
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.ui.conversationpart.chatpart.MessagingActivity;

import javax.inject.Inject;

public class MainConversationPage extends Fragment implements MainConversationRecyclerViewAdapter.OnClickConversationListener, ConversationInteractorCallback {

    private RecyclerView recyclerView;
    private NavController navController;
    private MainConversationRecyclerViewAdapter recyclerViewAdapter;

    @Inject
    ConversationInteractor conversationInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getComponent().injectFragment(this);

        View root = inflater.inflate(R.layout.fragment_main_conversation_page, container, false);
        recyclerView = root.findViewById(R.id.main_conversation_page_recycler_view);
        navController = NavHostFragment.findNavController(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        conversationInteractor.subscribe(this);

        recyclerViewAdapter = new MainConversationRecyclerViewAdapter(getContext(),
                conversationInteractor.getConversations(), this);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
    }

    @Override
    public void onClickConversation(int position) {
        Intent intent = new Intent(getContext(), MessagingActivity.class);
        intent.putExtra("correspondencePosition", position);
        startActivity(intent);

    }

    @Override
    public void conversationsDataUpdated() {
        recyclerViewAdapter.refreshData(conversationInteractor.getConversations());
    }

    @Override
    public void onResume() {
        super.onResume();
        conversationInteractor.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        conversationInteractor.subscribe(this);
    }
}
