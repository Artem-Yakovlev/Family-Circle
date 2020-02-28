package com.tydeya.familycircle.ui.conversationpart;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.domain.conversation.Conversation;

import java.util.ArrayList;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

public class MainConversationRecyclerViewAdapter
        extends RecyclerView.Adapter<MainConversationRecyclerViewAdapter.FamilyConversationViewHolder> {

    private Context context;
    private ArrayList<Conversation> conversations;
    private OnClickConversationListener onClickConversationListener;
    private final int CONVERSATION_WITH_UNREAD_MESSAGES = 0;
    private final int CONVERSATION_WITHOUT_UNREAD_MESSAGES = 1;

    MainConversationRecyclerViewAdapter(Context context, ArrayList<Conversation> conversations,
                                        OnClickConversationListener onClickConversationListener) {
        this.onClickConversationListener = onClickConversationListener;
        this.context = context;
        this.conversations = conversations;
    }

    @Override
    public int getItemViewType(int position) {
        if (conversations.get(position).getNumberUnreadMessages() != 0) {
            return CONVERSATION_WITH_UNREAD_MESSAGES;
        } else {
            return CONVERSATION_WITHOUT_UNREAD_MESSAGES;
        }
    }

    @NonNull
    @Override
    public MainConversationRecyclerViewAdapter.FamilyConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new FamilyConversationViewHolder(layoutInflater.inflate(R.layout.conversation_card_layout,
                parent, false), onClickConversationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainConversationRecyclerViewAdapter.FamilyConversationViewHolder holder, int position) {
        holder.setNameText(conversations.get(position).getDescription().getTitle());

        int conversationSize = conversations.get(position).getChatMessages().size();
        if (conversationSize != 0) {
            holder.setLastMessageText(conversations.get(position).getChatMessages().get(conversationSize - 1).getText());
        } else {
            holder.setLastMessageText("...");
        }

        switch (getItemViewType(position)) {
            case CONVERSATION_WITH_UNREAD_MESSAGES:
                holder.setBackgroundColor(context.getResources().getColor(R.color.colorTransparentBlue));
                holder.setBadgeNumberText(conversations.get(position).getNumberUnreadMessages());
                holder.setBadgeVisibility(true);
                break;
            case CONVERSATION_WITHOUT_UNREAD_MESSAGES:
                holder.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                holder.setBadgeVisibility(false);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    static class FamilyConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout mainLayout;

        private TextView nameText;
        private TextView lastMessageText;
        private ShapedImageView userShapedImage;
        private OnClickConversationListener onClickConversationListener;

        private LinearLayout badgeBlockLayout;
        private TextView badgeNumberText;

        FamilyConversationViewHolder(@NonNull View itemView, OnClickConversationListener onClickConversationListener) {
            super(itemView);
            this.onClickConversationListener = onClickConversationListener;

            mainLayout = itemView.findViewById(R.id.conversation_page_main_layout);

            nameText = itemView.findViewById(R.id.conversation_page_card_name);
            lastMessageText = itemView.findViewById(R.id.conversation_page_card_last_message);
            userShapedImage = itemView.findViewById(R.id.conversation_page_card_image);

            badgeBlockLayout = itemView.findViewById(R.id.conversation_page_card_badge_block);
            badgeNumberText = itemView.findViewById(R.id.conversation_page_card_badge_number);

            itemView.setOnClickListener(this);
        }

        void setNameText(String name) {
            nameText.setText(name);
        }

        void setLastMessageText(String lastMessage) {
            lastMessageText.setText(lastMessage);
        }

        void setImage(Uri imageUri) {
            Glide.with(nameText.getContext())
                    .load(imageUri)
                    .into(userShapedImage);
        }

        void setBadgeVisibility(boolean visible) {
            if (visible) {
                badgeBlockLayout.setVisibility(View.VISIBLE);
            } else {
                badgeBlockLayout.setVisibility(View.INVISIBLE);
            }
        }

        void setBadgeNumberText(int number) {
            badgeNumberText.setText(String.valueOf(number));
        }

        void setBackgroundColor(int color) {
            mainLayout.setBackgroundColor(color);
        }

        @Override
        public void onClick(View view) {
            onClickConversationListener.onClickConversation(getAdapterPosition());
        }
    }

    public interface OnClickConversationListener {

        void onClickConversation(int position);
    }

    void refreshData(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

}

