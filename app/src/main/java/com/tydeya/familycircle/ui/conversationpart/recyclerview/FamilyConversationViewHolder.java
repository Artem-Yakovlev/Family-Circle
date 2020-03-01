package com.tydeya.familycircle.ui.conversationpart.recyclerview;

import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

class FamilyConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private LinearLayout mainLayout;

    private TextView nameText;

    private TextView lastMessageText;
    private TextView lastMessageAuthor;
    private TextView lastMessageTime;

    private ShapedImageView userShapedImage;
    private OnClickConversationListener onClickConversationListener;

    private LinearLayout badgeBlockLayout;
    private TextView badgeNumberText;

    FamilyConversationViewHolder(@NonNull View itemView, OnClickConversationListener onClickConversationListener) {
        super(itemView);
        this.onClickConversationListener = onClickConversationListener;
        findAllViewHolderViewsById(itemView);
        itemView.setOnClickListener(this);
    }

    private void findAllViewHolderViewsById(View itemView) {
        mainLayout = itemView.findViewById(R.id.conversation_page_main_layout);

        nameText = itemView.findViewById(R.id.conversation_page_card_name);
        lastMessageText = itemView.findViewById(R.id.conversation_page_card_last_message_text);
        lastMessageAuthor = itemView.findViewById(R.id.conversation_page_card_last_message_author);
        lastMessageTime = itemView.findViewById(R.id.conversation_page_card_last_message_time);

        userShapedImage = itemView.findViewById(R.id.conversation_page_card_image);

        badgeBlockLayout = itemView.findViewById(R.id.conversation_page_card_badge_block);
        badgeNumberText = itemView.findViewById(R.id.conversation_page_card_badge_number);
    }

    void setNameText(String name) {
        nameText.setText(name);
    }

    void setLastMessageText(String lastMessage) {
        lastMessageText.setText(lastMessage);
    }

    void setLastMessageAuthor(String author) {
        lastMessageAuthor.setText(author);
    }

    void setLastMessageTime(String time) {
        lastMessageTime.setText(time);
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