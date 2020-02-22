package com.tydeya.familycircle.conversationpart.chatpart;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.domain.messages.ChatMessage;
import com.tydeya.familycircle.R;

import java.util.ArrayList;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatMessageViewHolder> {

    private Context context;
    private ArrayList<ChatMessage> chatMessages;
    private static final int OUTGOING_MESSAGE_VIEW_TYPE = 0;
    private static final int INBOX_MESSAGE_VIEW_TYPE = 1;
    private static final int INFORMATION_MESSAGE_VIEW_TYPE = 2;


    ChatRecyclerViewAdapter(Context context, ArrayList<ChatMessage> chatMessages) {
        this.context = context;
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatRecyclerViewAdapter.ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        switch (viewType) {
            case OUTGOING_MESSAGE_VIEW_TYPE:
                return new ChatMessageViewHolder(layoutInflater.inflate(R.layout.outgoing_message_card,
                        parent, false));
            case INBOX_MESSAGE_VIEW_TYPE:
                return new ChatMessageViewHolder(layoutInflater.inflate(R.layout.message_card_received,
                        parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO MESSAGE TYPE SWITCHER
        //if (((PersonMessage) messages.get(position)).getAuthor() == User.getInstance().getUserFamilyMember()) {
        //    return OUTGOING_MESSAGE_VIEW_TYPE;
        //} else {
        //    return INBOX_MESSAGE_VIEW_TYPE;
        //}
        return 1;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerViewAdapter.ChatMessageViewHolder holder, int position) {
        /*
        switch (getItemViewType(position)) {
            case OUTGOING_MESSAGE_VIEW_TYPE:
                holder.setMessageText(messages.get(position).getText());
                break;
            case INBOX_MESSAGE_VIEW_TYPE:
                holder.setAuthorText(((PersonMessage) messages.get(position)).getAuthor().getName());
                holder.setMessageText(messages.get(position).getText());
                break;
        }*/
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageText;
        private TextView authorText;
        private TextView messageTimeText;
        private Uri imageUri;

        ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            authorText = itemView.findViewById(R.id.message_text_name);
            messageText = itemView.findViewById(R.id.message_text_body);
            messageTimeText = itemView.findViewById(R.id.message_text_time);
        }

        void setAuthorText(String authorName) {
            this.authorText.setText(authorName);
        }

        void setMessageText(String messageBodyText) {
            this.messageText.setText(messageBodyText);
        }

        public void setMessageTimeText(String messageTime) {
            this.messageTimeText.setText(messageTime);
        }
    }

}

