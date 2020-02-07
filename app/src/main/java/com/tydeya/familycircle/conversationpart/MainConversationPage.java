package com.tydeya.familycircle.conversationpart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.family.conversation.Conversation;
import com.tydeya.familycircle.family.conversation.FamilyConversation;
import com.tydeya.familycircle.family.conversation.Message;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainConversationPage extends Fragment implements MainConversationRecyclerViewAdapter.OnClickConversationListener{

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_conversation_page, container, false);
        recyclerView = root.findViewById(R.id.main_conversation_page_recycler_view);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Message> messages = new ArrayList<>();
        Conversation conversation1 = new FamilyConversation(messages, "Main conf");
        Conversation conversation2 = new FamilyConversation(messages, "Second conf");
        ArrayList<Conversation> conversations = new ArrayList<>();
        conversations.add(conversation1);
        conversations.add(conversation2);

        MainConversationRecyclerViewAdapter recyclerViewAdapter = new MainConversationRecyclerViewAdapter(getContext(),
                conversations, new WeakReference<>(this));
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
    }

    @Override
    public void onClickConversation(int position) {

    }
}
