package com.tydeya.familycircle.conversationpart;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.family.conversation.FamilyConversation;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

public class MainConversationRecyclerViewAdapter
        extends RecyclerView.Adapter<MainConversationRecyclerViewAdapter.FamilyConversationViewHolder> {

    private Context context;
    private ArrayList<FamilyConversation> conversations;
    private WeakReference<OnClickConversationListener> onClickConversationListener;

    MainConversationRecyclerViewAdapter(Context context, ArrayList<FamilyConversation> conversations,
                                        WeakReference<OnClickConversationListener> onClickConversationListener) {
        this.onClickConversationListener = onClickConversationListener;
        this.context = context;
        this.conversations = conversations;
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
        holder.setNameText(conversations.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    static class FamilyConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameText;
        private TextView lastMessageText;
        private ShapedImageView userShapedImage;
        private WeakReference<OnClickConversationListener> onClickConversationListener;

        FamilyConversationViewHolder(@NonNull View itemView, WeakReference<OnClickConversationListener> onClickConversationListener) {
            super(itemView);
            this.onClickConversationListener = onClickConversationListener;

            nameText = itemView.findViewById(R.id.conversation_page_card_name);
            lastMessageText = itemView.findViewById(R.id.conversation_page_card_last_message);
            userShapedImage = itemView.findViewById(R.id.conversation_page_card_image);
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

        @Override
        public void onClick(View view) {
            onClickConversationListener.get().onClickConversation(getAdapterPosition());
        }
    }

    public interface OnClickConversationListener {

        void onClickConversation(int position);
    }

    void refreshData(ArrayList<FamilyConversation> conversations) {
        this.conversations.clear();
        this.conversations = conversations;
        notifyDataSetChanged();
    }

}

