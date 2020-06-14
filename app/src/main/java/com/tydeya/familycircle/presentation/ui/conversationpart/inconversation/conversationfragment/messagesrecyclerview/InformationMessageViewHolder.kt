package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview

import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.databinding.RecyclerItemInformationChatMessageBinding
import com.tydeya.familycircle.presentation.ui.utils.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

class InformationMessageViewHolder(
        private val binding: RecyclerItemInformationChatMessageBinding
) :
        BaseViewHolder<FullChatMessage>(binding.root) {

    override fun bindData(item: FullChatMessage) {
        binding.root.text = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
                .format(item.chatMessage.dateTime)
    }

}
