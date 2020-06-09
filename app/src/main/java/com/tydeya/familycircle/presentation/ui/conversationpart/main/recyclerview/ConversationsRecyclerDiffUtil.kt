package com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.messenger.conversation.ConversationPreview

class ConversationsRecyclerDiffUtil(
        private val oldList: List<ConversationPreview>,
        private val newList: List<ConversationPreview>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}
