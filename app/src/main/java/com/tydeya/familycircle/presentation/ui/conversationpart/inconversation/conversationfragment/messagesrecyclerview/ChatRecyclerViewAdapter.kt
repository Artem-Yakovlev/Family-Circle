package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.data.familymember.FamilyMember
import com.tydeya.familycircle.data.messenger.chat.ChatMessage
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.databinding.RecyclerItemInformationChatMessageBinding
import com.tydeya.familycircle.databinding.RecyclerItemOutgoingChatmessageBinding
import com.tydeya.familycircle.databinding.RecyclerItemReceiverdMessageBinding
import com.tydeya.familycircle.presentation.ui.utils.BaseViewHolder
import com.tydeya.familycircle.utils.extensions.getUserPhone
import com.tydeya.familycircle.utils.extensions.toArrayList
import com.tydeya.familycircle.utils.extensions.toFullMessages
import org.joda.time.DateTimeComparator


class ChatRecyclerViewAdapter(
        private val chatMessages: ArrayList<ChatMessage> = ArrayList(),
        private val familyMembers: ArrayList<FamilyMember> = ArrayList(),
        private val fullChatMessages: ArrayList<FullChatMessage> = ArrayList(),
        private val listener: InConversationViewHolderListener
) : RecyclerView.Adapter<BaseViewHolder<FullChatMessage>>() {

    /**
     * View holder create
     * */

    override fun getItemViewType(position: Int): Int {
        return when (fullChatMessages[position].chatMessage.authorPhoneNumber) {
            getUserPhone() -> {
                OUTGOING_MESSAGE_VIEW_TYPE
            }
            INFORMATION_MESSAGE_TAG -> {
                INFORMATION_MESSAGE_VIEW_TYPE
            }
            else -> {
                INBOX_MESSAGE_VIEW_TYPE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<FullChatMessage> {
        return when (viewType) {
            OUTGOING_MESSAGE_VIEW_TYPE -> OutgoingMessageViewHolder(
                    binding = RecyclerItemOutgoingChatmessageBinding.inflate(
                            LayoutInflater.from(parent.context), parent, false
                    ),
                    listener = listener
            )
            INBOX_MESSAGE_VIEW_TYPE -> InboxMessageViewHolder(RecyclerItemReceiverdMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            ))
            else -> InformationMessageViewHolder(RecyclerItemInformationChatMessageBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            ))
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<FullChatMessage>, position: Int) {
        holder.bindData(fullChatMessages[position])
    }

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

        val dateComparator = DateTimeComparator.getDateOnlyInstance()
        val formattedMessages = fullChatMessages
                .fold(ArrayList<FullChatMessage>()) { acc, message ->

                    if (acc.isEmpty() || dateComparator.compare(acc.last().chatMessage.dateTime,
                                    message.chatMessage.dateTime) != 0) {

                        acc.add(FullChatMessage(chatMessage = ChatMessage(
                                authorPhoneNumber = INFORMATION_MESSAGE_TAG,
                                dateTime = message.chatMessage.dateTime
                        )))
                    }
                    acc.apply { add(message) }
                }

        val diffResult = DiffUtil.calculateDiff(FullMessagesRecyclerDiffUtil(
                this.fullChatMessages, formattedMessages
        ))

        this.fullChatMessages.clear()
        this.fullChatMessages.addAll(formattedMessages)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = fullChatMessages.size

    companion object {

        const val OUTGOING_MESSAGE_VIEW_TYPE = 0
        const val INBOX_MESSAGE_VIEW_TYPE = 1
        const val INFORMATION_MESSAGE_VIEW_TYPE = 2

        const val INFORMATION_MESSAGE_TAG = "information"
    }
}
