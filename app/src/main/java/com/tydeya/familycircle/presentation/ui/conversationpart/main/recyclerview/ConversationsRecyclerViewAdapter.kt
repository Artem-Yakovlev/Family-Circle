package com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.conversation.Conversation

class ConversationsRecyclerViewAdapter(
        var conversations: ArrayList<Conversation>,
        val clickListener: ConversationsRecyclerViewOnClickListener
) :
        RecyclerView.Adapter<ConversationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ConversationViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.cardview_conversation, parent, false),
                    clickListener)

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bindData(conversations[position])
    }

    fun refreshData(conversations: ArrayList<Conversation>) {
        val diffResult = DiffUtil.calculateDiff(ConversationsRecyclerDiffUtil(
                this.conversations, conversations
        ))

        this.conversations.clear()
        this.conversations.addAll(conversations)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = conversations.size

}
