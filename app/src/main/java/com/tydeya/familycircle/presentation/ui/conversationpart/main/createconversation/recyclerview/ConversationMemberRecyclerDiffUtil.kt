package com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.tydeya.familycircle.data.messenger.ConversationMember

class ConversationMemberRecyclerDiffUtil(
        private val oldList: List<ConversationMember>,
        private val newList: List<ConversationMember>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].phoneNumber == newList[newItemPosition].phoneNumber

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

}
