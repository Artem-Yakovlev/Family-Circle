package com.tydeya.familycircle.ui.conversationpart.recyclerview;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tydeya.familycircle.App;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.domain.familyassistant.abstraction.FamilyAssistant;
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl;
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor;
import com.tydeya.familycircle.data.chatmessage.ChatMessage;
import com.tydeya.familycircle.data.conversation.Conversation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

public class MainConversationRecyclerViewAdapter extends RecyclerView.Adapter<FamilyConversationViewHolder> {

    private Context context;
    private ArrayList<Conversation> conversations;
    private OnClickConversationListener onClickConversationListener;
    private final int CONVERSATION_WITH_UNREAD_MESSAGES = 0;
    private final int CONVERSATION_WITHOUT_UNREAD_MESSAGES = 1;

    @Inject
    FamilyInteractor familyInteractor;

    private FamilyAssistant familyAssistant;

    public MainConversationRecyclerViewAdapter(Context context, ArrayList<Conversation> conversations,
                                               OnClickConversationListener onClickConversationListener) {
        App.getComponent().injectRecyclerViewAdapter(this);
        this.familyAssistant = new FamilyAssistantImpl(familyInteractor.getActualFamily());
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
    public FamilyConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        return new FamilyConversationViewHolder(layoutInflater.inflate(R.layout.conversation_card_layout,
                parent, false), onClickConversationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyConversationViewHolder holder, int position) {
        holder.setNameText(conversations.get(position).getDescription().getTitle());

        int conversationSize = conversations.get(position).getChatMessages().size();
        if (conversationSize != 0) {

            String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "hh:mm aa");
            SimpleDateFormat formatForDateNow = new SimpleDateFormat(pattern, Locale.getDefault());

            ChatMessage lastChatMessage = conversations.get(position).getChatMessages().get(conversationSize - 1);
            String lastMessageAuthorName = familyAssistant.getUserByPhone(lastChatMessage.getAuthorPhoneNumber())
                    .getDescription().getName();

            holder.setLastMessageAuthor(lastMessageAuthorName + ": ");
            holder.setLastMessageText(lastChatMessage.getText());
            holder.setLastMessageTime(formatForDateNow.format(lastChatMessage.getDateTime()));
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

    public void refreshData(ArrayList<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

}

