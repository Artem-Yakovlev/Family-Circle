package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage

class FullMessagesRecyclerDiffUtil(
        private val oldList: List<FullChatMessage>,
        private val newList: List<FullChatMessage>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].chatMessage.id == newList[newItemPosition].chatMessage.id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.isUserOnline == newItem.isUserOnline
                && oldItem.chatMessage == newItem.chatMessage
                && oldItem.imageAddress == newItem.imageAddress
                && oldItem.whoSawMessage.size == newItem.whoSawMessage.size
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}
