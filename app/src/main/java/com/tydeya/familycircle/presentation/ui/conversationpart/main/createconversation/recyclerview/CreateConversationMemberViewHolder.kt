package com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.familymember.FamilyMember
import kotlinx.android.synthetic.main.cardview_member_in_create_conversation_dialog.view.*

class CreateConversationMemberViewHolder(itemView: View,
                                         private val listener: CreateConversationMembersCheckBoxListener)
    :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(familyMember: FamilyMember) {
        itemView.member_in_create_conversation_dialog_name.text = familyMember.description.name

        itemView.member_in_create_conversation_dialog_checkbox.setOnClickListener {
            listener.checkboxChangeState(familyMember.fullPhoneNumber,
                    itemView.member_in_create_conversation_dialog_checkbox.isChecked)
        }
    }

}