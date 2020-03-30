package com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.recyclerview

import android.text.format.DateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.chatmessage.ChatMessage
import com.tydeya.familycircle.utils.getDp
import kotlinx.android.synthetic.main.message_card_received.view.*
import kotlinx.android.synthetic.main.outgoing_message_card.view.*
import java.text.SimpleDateFormat
import java.util.*

class InConversationChatViewHolder(itemView: View,
                                   private val messageType: Int
) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(message: ChatMessage, name: String, imageAddress: String) {

        val pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "hh:mm aa")
        val formatForDateNow = SimpleDateFormat(pattern, Locale.getDefault())
        val time = formatForDateNow.format(message.dateTime)

        if (messageType == OUTGOING_MESSAGE_VIEW_TYPE) {
            setOutGoingMessageData(message, time)
        } else {
            setReceivedMessageData(message, time, name, imageAddress)
        }
    }

    private fun setOutGoingMessageData(message: ChatMessage, time: String) {
        itemView.outgoing_message_text_body.text = message.text
        itemView.outgoing_message_text_time.text = time
    }

    private fun setReceivedMessageData(message: ChatMessage, time: String,
                                       name: String, imageAddress: String) {

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


    }

}