package com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.messenger.conversation.Conversation

class ConversationsRecyclerDiffUtil(
        private val oldList: List<Conversation>,
        private val newList: List<Conversation>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldConversation = oldList[oldItemPosition]
        val newConversation = newList[newItemPosition]
        return oldConversation.title == newConversation.title
                && oldConversation.unreadMessagesCounter == newConversation.unreadMessagesCounter
                && oldConversation.messages == newConversation.messages
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}
