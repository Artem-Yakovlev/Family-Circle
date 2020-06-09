package com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.messenger.conversation.ConversationMember
import kotlinx.android.synthetic.main.cardview_member_in_create_conversation_dialog.view.*

class CreateConversationMemberViewHolder(
        itemView: View
) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(conversationMember: ConversationMember) {
        itemView.member_in_create_conversation_dialog_name.text = conversationMember.name

        itemView.member_in_create_conversation_dialog_checkbox.setOnClickListener {
            conversationMember.isChecked = itemView
                    .member_in_create_conversation_dialog_checkbox.isChecked
        }
    }
}
