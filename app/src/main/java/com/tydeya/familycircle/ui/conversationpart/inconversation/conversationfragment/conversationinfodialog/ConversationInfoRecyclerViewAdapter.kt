package com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R

class ConversationInfoRecyclerViewAdapter(
        private val context: Context,
        private val membersNames: ArrayList<String>
) :
        RecyclerView.Adapter<ConversationInfoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ConversationInfoViewHolder(LayoutInflater
                    .from(context)
                    .inflate(R.layout.cardview_member_in_conversation_info_dialog, parent, false))

    override fun getItemCount() = membersNames.size

    override fun onBindViewHolder(holder: ConversationInfoViewHolder, position: Int) {
        holder.bindData(membersNames[position])
    }

}