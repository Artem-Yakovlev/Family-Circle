package com.tydeya.familycircle.conversationpart.chatpart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.R;
import com.tydeya.familycircle.user.User;

public class CorrespondenceFragment extends Fragment {

    private View root;
    private RecyclerView chatRecyclerView;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_correspondence, container, false);
        chatRecyclerView = root.findViewById(R.id.chat_recycler_view);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(getContext(), User.getInstance().getFamily().getFamilyConversations().get(0).getMessages());
        chatRecyclerView.setAdapter(chatRecyclerViewAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

    }
}
