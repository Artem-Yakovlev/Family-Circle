package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview

import android.text.format.DateFormat
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.databinding.RecyclerItemOutgoingChatmessageBinding
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview.viewiconrecyclerview.WhoSawMessageRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.utils.BaseViewHolder
import java.text.SimpleDateFormat
import java.util.*

class OutgoingMessageViewHolder(
        private val binding: RecyclerItemOutgoingChatmessageBinding,
        private val listener: InConversationViewHolderListener
) :
        BaseViewHolder<FullChatMessage>(binding.root) {

    override fun bindData(item: FullChatMessage) {
        val formatForDateNow = SimpleDateFormat(DateFormat.getBestDateTimePattern(
                Locale.getDefault(), "hh:mm aa"), Locale.getDefault()
        )

        binding.outgoingMessageTextBody.text = item.chatMessage.text
        binding.outgoingMessageTextTime.text = formatForDateNow.format(item.chatMessage.dateTime)

        binding.root.isLongClickable = true
        binding.root.setOnLongClickListener {
            listener.showMessageEditingMenu(item.chatMessage.id, it)
            return@setOnLongClickListener true
        }

        with(binding.whoSawMessageRecyclerView) {
            layoutManager = LinearLayoutManager(
                    itemView.context, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = WhoSawMessageRecyclerViewAdapter(item.whoSawMessage)
        }

    }
}
