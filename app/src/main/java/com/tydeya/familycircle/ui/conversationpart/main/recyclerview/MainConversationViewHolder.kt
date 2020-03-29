package com.tydeya.familycircle.ui.conversationpart.main.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import kotlinx.android.synthetic.main.cardview_conversation.view.*

class MainConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(conversation: Conversation) {
        itemView.conversation_page_card_name.text = conversation.title
    }

}