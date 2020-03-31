package com.tydeya.familycircle.ui.conversationpart.main.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.conversation.Conversation

class MainConversationRecyclerViewAdapter(val context: Context,
                                          var conversations: ArrayList<Conversation>,
                                          val clickListener: MainConversationRecyclerViewOnClickListener
) :
        RecyclerView.Adapter<MainConversationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MainConversationViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.cardview_conversation, parent, false), clickListener)

    override fun onBindViewHolder(holder: MainConversationViewHolder, position: Int) {
        holder.bindData(context, conversations[position])
    }

    override fun getItemCount() = conversations.size

    fun refreshData(conversations: ArrayList<Conversation>) {
        this.conversations = conversations
        notifyDataSetChanged()
    }

}