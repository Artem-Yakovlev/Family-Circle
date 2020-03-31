package com.tydeya.familycircle.ui.conversationpart.main.createconversation.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.FamilyMember

class CreateConversationMembersRecyclerViewAdapter(
        private val context: Context,
        private val familyMembers: ArrayList<FamilyMember>,
        private val listener: CreateConversationMembersCheckBoxListener
) :
        RecyclerView.Adapter<CreateConversationMemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CreateConversationMemberViewHolder(
                    LayoutInflater.from(context).inflate(
                            R.layout.cardview_member_in_create_conversation_dialog,
                            parent, false),
                    listener)

    override fun getItemCount() = familyMembers.size

    override fun onBindViewHolder(holder: CreateConversationMemberViewHolder, position: Int) {
        holder.bindData(familyMembers[position])
    }
}