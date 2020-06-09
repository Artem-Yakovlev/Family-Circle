package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview

import android.text.format.DateFormat
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.chat.ChatMessage
import com.tydeya.familycircle.data.messenger.chat.FullChatMessage
import com.tydeya.familycircle.utils.getDp
import kotlinx.android.synthetic.main.message_card_received.view.*
import kotlinx.android.synthetic.main.outgoing_message_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class InConversationChatViewHolder(
        itemView: View,
        private val messageType: Int,
        private val listener: InConversationViewHolderListener
) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(fullChatMessage: FullChatMessage) {
        val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "hh:mm aa")
        val formatForDateNow = SimpleDateFormat(pattern, Locale.getDefault())
        val time = formatForDateNow.format(fullChatMessage.chatMessage.dateTime)

        if (messageType == OUTGOING_MESSAGE_VIEW_TYPE) {
            setOutGoingMessageData(fullChatMessage.chatMessage, time, listener)
        } else {
            setReceivedMessageData(fullChatMessage.chatMessage, time, fullChatMessage.userName,
                    fullChatMessage.imageAddress, fullChatMessage.isUserOnline)
        }
    }

    private fun setOutGoingMessageData(
            message: ChatMessage,
            time: String,
            listener: InConversationViewHolderListener
    ) {
        with(itemView) {
            outgoing_message_text_body.text = message.text
            outgoing_message_text_time.text = time

            isLongClickable = true
            setOnLongClickListener {
                listener.showMessageEditingMenu(message.id, it)
                return@setOnLongClickListener true
            }
        }
    }

    private fun setReceivedMessageData(
            message: ChatMessage,
            time: String,
            name: String,
            imageAddress: String,
            isUserOnline: Boolean
    ) {
        itemView.received_message_text_body.text = message.text
        itemView.received_message_text_time.text = time
        itemView.received_message_text_name.text = name

        if (imageAddress != "") {
            Glide.with(itemView.context)
                    .load(imageAddress)
                    .into(itemView.received_message_author_image)
            itemView.received_message_author_image.setPadding(0, 0, 0, 0)
        } else {
            Glide.with(itemView.context)
                    .load(R.drawable.ic_photo_camera_blue_36dp)
                    .into(itemView.received_message_author_image)
            val padding = getDp(itemView.context, 4)
            itemView.received_message_author_image.setPadding(padding, padding, padding, padding)
        }

        if (isUserOnline) {
            itemView.received_message_author_image.setStrokeColor(ContextCompat
                    .getColor(itemView.context, R.color.colorOnlineGreen))
        } else {
            itemView.received_message_author_image.setStrokeColor(ContextCompat
                    .getColor(itemView.context, R.color.colorTransparentGray))
        }

    }

}
