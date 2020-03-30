package com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cardview_member_in_conversation_info_dialog.view.*
import java.text.FieldPosition

class ConversationInfoViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(name: String) {
        itemView.member_in_conversation_info_dialog_name.text = name
    }
}