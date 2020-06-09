package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.FamilyMember

class ConversationInfoRecyclerViewAdapter(
        private val members: ArrayList<FamilyMember> = ArrayList()
) :
        RecyclerView.Adapter<ConversationInfoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ConversationInfoViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.cardview_member_in_conversation_info_dialog,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: ConversationInfoViewHolder, position: Int) {
        holder.bindData(members[position].description.name)
    }

    fun refreshData(members: ArrayList<FamilyMember>) {
        this.members.clear()
        this.members.addAll(members)
        notifyDataSetChanged()
    }

    override fun getItemCount() = members.size
}
