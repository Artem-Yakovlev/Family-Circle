package com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details;


import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.domain.userinteractor.details.UserInteractor;
import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.familymember.FamilyMember;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ChatMessageViewHolder> {

    private Context context;
    private ArrayList<ChatMessage> messages;
    private static final int OUTGOING_MESSAGE_VIEW_TYPE = 0;
    private static final int INBOX_MESSAGE_VIEW_TYPE = 1;
    private static final int INFORMATION_MESSAGE_VIEW_TYPE = 2;

    @Inject
    FamilyInteractor familyInteractor;

    @Inject
    UserInteractor userInteractor;

    ChatRecyclerViewAdapter(Context context, ArrayList<ChatMessage> messages) {
        App.getComponent().injectRecyclerViewAdapter(this);
        this.context = context;
        this.messages = messages;
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

        if ((messages.get(position)).getAuthorPhoneNumber()
                .equals(userInteractor.getUserAccountFamilyMember().getFullPhoneNumber())) {
            return OUTGOING_MESSAGE_VIEW_TYPE;
        } else {
            return INBOX_MESSAGE_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatRecyclerViewAdapter.ChatMessageViewHolder holder, int position) {

        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "hh:mm aa");
        SimpleDateFormat formatForDateNow = new SimpleDateFormat(pattern, Locale.getDefault());

        switch (getItemViewType(position)) {
            case OUTGOING_MESSAGE_VIEW_TYPE:
                holder.setMessageText(messages.get(position).getText());
                holder.setMessageTimeText(formatForDateNow.format(messages.get(position).getDateTime()));
                break;
            case INBOX_MESSAGE_VIEW_TYPE:
                holder.setAuthorText(getNameByFullNumber(messages.get(position).getAuthorPhoneNumber()));
                holder.setMessageText(messages.get(position).getText());
                holder.setMessageTimeText(formatForDateNow.format(messages.get(position).getDateTime()));
                holder.setProfileImage(getImageAddressByFullNumber(messages.get(position).getAuthorPhoneNumber()));
                break;
        }
    }

    private String getNameByFullNumber(String fullPhoneNumber) {
        FamilyMember familyMember = familyInteractor.getFamilyAssistant().getUserByPhone(fullPhoneNumber);
        if (familyMember != null) {
            return familyMember.getDescription().getName();
        } else {
            return context.getString(R.string.unknown_text);
        }
    }

    private String getImageAddressByFullNumber(String fullPhoneNumber) {
        FamilyMember familyMember = familyInteractor.getFamilyAssistant().getUserByPhone(fullPhoneNumber);
        if (familyMember != null) {
            return familyMember.getDescription().getImageAddress();
        } else {
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageText;
        private TextView authorText;
        private TextView messageTimeText;
        private ImageView profileImage;

        ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            authorText = itemView.findViewById(R.id.message_text_name);
            messageText = itemView.findViewById(R.id.message_text_body);
            messageTimeText = itemView.findViewById(R.id.message_text_time);
            profileImage = itemView.findViewById(R.id.message_author_image);
        }

        void setAuthorText(String authorName) {
            this.authorText.setText(authorName);
        }

        void setMessageText(String messageBodyText) {
            this.messageText.setText(messageBodyText);
        }

        void setMessageTimeText(String messageTime) {
            this.messageTimeText.setText(messageTime);
        }

        void setProfileImage(String imageAddress) {
            Glide.with(itemView.getContext()).load(imageAddress).into(profileImage);
        }
    }

    void refreshData(ArrayList<ChatMessage> chatMessages) {
        this.messages = chatMessages;
        notifyDataSetChanged();
    }


}

