package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview_member_in_conversation_add_member_dialog.view.*

class ConversationAddMemberDialogViewHolder(
        itemView: View,
        private val listener: ConversationAddMemberDialogRecyclerViewClickListener
) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(name: String, position: Int) {
        itemView.member_in_conversation_add_member_dialog_name.text = name
        itemView.member_in_conversation_add_member_dialog_name_add_button.setOnClickListener {
            listener.onAddMemberButtonClick(position)
        }
    }
}