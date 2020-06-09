package com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.data.messenger.conversation.ConversationPreview
import com.tydeya.familycircle.utils.extensions.toArrayList
import com.tydeya.familycircle.utils.extensions.toConversationsPreview

class ConversationsRecyclerViewAdapter(
        var conversationsPreview: ArrayList<ConversationPreview> = ArrayList(),
        val clickListener: ConversationsRecyclerViewOnClickListener
) :
        RecyclerView.Adapter<ConversationViewHolder>() {

    private val conversations: ArrayList<Conversation> = ArrayList()
    private val familyMembers: ArrayList<FamilyMember> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ConversationViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.cardview_conversation, parent, false),
                    clickListener)

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        holder.bindData(conversationsPreview[position])
    }

    fun refreshMembers(members: ArrayList<FamilyMember>) {
        this.familyMembers.clear()
        this.familyMembers.addAll(members)
        refreshData(conversations.toConversationsPreview(familyMembers).toArrayList())
    }

    fun refreshConversations(conversations: ArrayList<Conversation>) {
        this.conversations.clear()
        this.conversations.addAll(conversations)
        refreshData(conversations.toConversationsPreview(familyMembers).toArrayList())
    }

    fun refreshData(conversationsPreview: ArrayList<ConversationPreview>) {
        val diffResult = DiffUtil.calculateDiff(ConversationsRecyclerDiffUtil(
                this.conversationsPreview, conversationsPreview
        ))

        this.conversationsPreview.clear()
        this.conversationsPreview.addAll(conversationsPreview)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = conversationsPreview.size

}
