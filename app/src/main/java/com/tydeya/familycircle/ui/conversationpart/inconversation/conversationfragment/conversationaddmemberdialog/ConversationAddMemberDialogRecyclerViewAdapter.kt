package com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog.ConversationInfoRecyclerViewAdapter

class ConversationAddMemberDialogRecyclerViewAdapter(
        private val context: Context,
        private val membersNames: ArrayList<String>,
        private val listener: ConversationAddMemberDialogRecyclerViewClickListener
) :
        RecyclerView.Adapter<ConversationAddMemberDialogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ConversationAddMemberDialogViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.cardview_member_in_conversation_add_member_dialog,
                            parent, false),
                    listener)

    override fun getItemCount() = membersNames.size

    override fun onBindViewHolder(holder: ConversationAddMemberDialogViewHolder, position: Int) {
        holder.bindData(membersNames[position], position)
    }

}