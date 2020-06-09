package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.data.messenger.conversation.Conversation

class FullMessagesRecyclerDiffUtil(
        private val oldList: List<FullChatMessage>,
        private val newList: List<FullChatMessage>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].chatMessage.id == newList[newItemPosition].chatMessage.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}
