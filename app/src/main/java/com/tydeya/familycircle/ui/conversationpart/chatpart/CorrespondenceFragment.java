package com.tydeya.familycircle.ui.conversationpart.chatpart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.data.conversationsinteractor.details.ConversationInteractor;
import com.tydeya.familycircle.domain.chatmessage.ChatMessage;

import javax.inject.Inject;

public class CorrespondenceFragment extends Fragment {

    private View root;
    private RecyclerView chatRecyclerView;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;
    private ImageButton sendButton;
    private TextInputEditText inputField;

    @Inject
    ConversationInteractor conversationInteractor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_correspondence, container, false);

        chatRecyclerView = root.findViewById(R.id.chat_recycler_view);
        sendButton = root.findViewById(R.id.chat_send_message_button);
        inputField = root.findViewById(R.id.chat_input_field);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        App.getComponent().injectFragment(this);

        chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(getContext(),
                conversationInteractor.getConversations().get(MessagingActivity.correspondencePosition).getChatMessages());

        chatRecyclerView.setAdapter(chatRecyclerViewAdapter);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        sendButton.setOnClickListener((view -> {
            if (!inputField.getText().toString().equals("")) {
                ChatMessage chatMessage = new ChatMessage("+79053333333", inputField.getText().toString(), null);
                conversationInteractor.getConversations().get(MessagingActivity.correspondencePosition).addMessage(chatMessage);
                inputField.setText("");
                chatRecyclerView.scrollToPosition(chatRecyclerViewAdapter.getItemCount() - 1);
            }
        }));
    }
}
