package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.FamilyMember

class ConversationAddMemberDialogRecyclerViewAdapter(
        private val members: ArrayList<FamilyMember> = ArrayList(),
        private val listener: ConversationAddMemberDialogRecyclerViewClickListener
) :
        RecyclerView.Adapter<ConversationAddMemberDialogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ConversationAddMemberDialogViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.cardview_member_in_conversation_add_member_dialog, parent,
                            false), listener)

    override fun onBindViewHolder(holder: ConversationAddMemberDialogViewHolder, position: Int) {
        holder.bindData(members[position])
    }

    fun refreshData(members: ArrayList<FamilyMember>) {
        this.members.clear()
        this.members.addAll(members)
        notifyDataSetChanged()
    }

    override fun getItemCount() = members.size

}
