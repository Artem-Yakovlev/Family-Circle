package com.tydeya.familycircle.conversationpart;

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

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.conversationpart.chatpart.MessagingActivity;
import com.tydeya.familycircle.user.User;

import java.lang.ref.WeakReference;

public class MainConversationPage extends Fragment implements MainConversationRecyclerViewAdapter.OnClickConversationListener,
ConversationUpdatedResultRecipient{

    private RecyclerView recyclerView;
    private NavController navController;
    private MainConversationRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_conversation_page, container, false);
        recyclerView = root.findViewById(R.id.main_conversation_page_recycler_view);
        navController = NavHostFragment.findNavController(this);
        User.getInstance().updateConversationData(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewAdapter = new MainConversationRecyclerViewAdapter(getContext(),
                User.getInstance().getFamily().getFamilyConversations(), new WeakReference<>(this));
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
    public void conversationDataUpdated() {
        recyclerViewAdapter.refreshData(User.getInstance().getFamily().getFamilyConversations());
    }
}
