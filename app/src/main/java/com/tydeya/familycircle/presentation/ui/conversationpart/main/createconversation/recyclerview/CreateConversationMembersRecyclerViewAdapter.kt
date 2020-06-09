package com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.conversation.ConversationMember

class CreateConversationMembersRecyclerViewAdapter(
        val members: ArrayList<ConversationMember>
) :
        RecyclerView.Adapter<CreateConversationMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CreateConversationMemberViewHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.cardview_member_in_create_conversation_dialog,
                                    parent, false))

    override fun onBindViewHolder(holder: CreateConversationMemberViewHolder, position: Int) {
        holder.bindData(members[position])
    }

    fun refreshData(members: ArrayList<ConversationMember>) {
        val diffResult = DiffUtil.calculateDiff(ConversationMemberRecyclerDiffUtil(
                this.members, members
        ))
        this.members.clear()
        this.members.addAll(members)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = members.size

}
