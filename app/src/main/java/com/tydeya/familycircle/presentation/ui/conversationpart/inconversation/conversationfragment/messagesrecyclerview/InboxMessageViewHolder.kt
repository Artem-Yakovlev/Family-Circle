package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview

import android.text.format.DateFormat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.databinding.RecyclerItemReceiverdMessageBinding
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.messagesrecyclerview.viewiconrecyclerview.WhoSawMessageRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.utils.BaseViewHolder
import com.tydeya.familycircle.utils.getDp
import java.text.SimpleDateFormat
import java.util.*

class InboxMessageViewHolder(
        private val binding: RecyclerItemReceiverdMessageBinding
) :
        BaseViewHolder<FullChatMessage>(binding.root) {

    override fun bindData(item: FullChatMessage) {
        val formatForDateNow = SimpleDateFormat(DateFormat.getBestDateTimePattern(
                Locale.getDefault(), "hh:mm aa"), Locale.getDefault()
        )
        binding.receivedMessageTextBody.text = item.chatMessage.text
        binding.receivedMessageTextTime.text = formatForDateNow.format(item.chatMessage.dateTime)
        binding.receivedMessageTextName.text = item.userName

        if (item.imageAddress != "") {
            Glide.with(itemView.context)
                    .load(item.imageAddress)
                    .into(binding.receivedMessageAuthorImage)
            binding.receivedMessageAuthorImage.setPadding(0, 0, 0, 0)
        } else {
            Glide.with(itemView.context)
                    .load(R.drawable.ic_photo_camera_blue_36dp)
                    .into(binding.receivedMessageAuthorImage)
            val padding = getDp(itemView.context, 4)
            binding.receivedMessageAuthorImage.setPadding(padding, padding, padding, padding)
        }

        if (item.isUserOnline) {
            binding.receivedMessageAuthorImage.setStrokeColor(ContextCompat
                    .getColor(itemView.context, R.color.colorOnlineGreen))
        } else {
            binding.receivedMessageAuthorImage.setStrokeColor(ContextCompat
                    .getColor(itemView.context, R.color.colorTransparentGray))
        }

        with(binding.whoSawMessageRecyclerView) {
            layoutManager = LinearLayoutManager(
                    itemView.context, LinearLayoutManager.HORIZONTAL, false
            )
            adapter = WhoSawMessageRecyclerViewAdapter(item.whoSawMessage)
        }
    }
}
