package com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.messenger.conversation.ConversationMember
import kotlinx.android.synthetic.main.recycler_item_checkbox_member_in_dialog.view.*

class CreateConversationMemberViewHolder(
        itemView: View
) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(conversationMember: ConversationMember) {
        itemView.checkbox_member_in_dialog_name.text = conversationMember.name

        itemView.checkbox_member_in_dialog_checkbox.setOnClickListener {
            conversationMember.isChecked = itemView
                    .checkbox_member_in_dialog_checkbox.isChecked
        }
    }
}
