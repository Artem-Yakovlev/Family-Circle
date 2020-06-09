package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.messenger.chat.ChatMessage
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.utils.extensions.getUserPhone
import com.tydeya.familycircle.utils.extensions.toArrayList
import com.tydeya.familycircle.utils.extensions.toFullMessages

const val OUTGOING_MESSAGE_VIEW_TYPE = 0
const val INBOX_MESSAGE_VIEW_TYPE = 1

class ChatRecyclerViewAdapter(
        private val chatMessages: ArrayList<ChatMessage> = ArrayList(),
        private val familyMembers: ArrayList<FamilyMember> = ArrayList(),
        private val fullChatMessages: ArrayList<FullChatMessage> = ArrayList()
) : RecyclerView.Adapter<InConversationChatViewHolder>() {

    /**
     * View holder create
     * */

    override fun getItemViewType(position: Int): Int {
        return if (fullChatMessages[position].chatMessage.authorPhoneNumber == getUserPhone()) {
            OUTGOING_MESSAGE_VIEW_TYPE
        } else {
            INBOX_MESSAGE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InConversationChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    when (viewType) {
                        OUTGOING_MESSAGE_VIEW_TYPE -> R.layout.outgoing_message_card
                        else -> R.layout.message_card_received
                    },
                    parent, false), viewType)

    override fun onBindViewHolder(holder: InConversationChatViewHolder, position: Int) {
        holder.bindData(fullChatMessages[position])
    }


    override fun getItemCount() = fullChatMessages.size

    fun refreshChatMessages(chatMessages: ArrayList<ChatMessage>) {
        this.chatMessages.clear()
        this.chatMessages.addAll(chatMessages)
        refreshData(chatMessages.toFullMessages(familyMembers).toArrayList())
    }

    fun refreshFamilyMembers(familyMembers: ArrayList<FamilyMember>) {
        this.familyMembers.clear()
        this.familyMembers.addAll(familyMembers)
        refreshData(chatMessages.toFullMessages(familyMembers).toArrayList())
    }

    private fun refreshData(fullChatMessages: ArrayList<FullChatMessage>) {
        val diffResult = DiffUtil.calculateDiff(FullMessagesRecyclerDiffUtil(
                this.fullChatMessages, fullChatMessages
        ))

        this.fullChatMessages.clear()
        this.fullChatMessages.addAll(fullChatMessages)

        diffResult.dispatchUpdatesTo(this)
    }
}