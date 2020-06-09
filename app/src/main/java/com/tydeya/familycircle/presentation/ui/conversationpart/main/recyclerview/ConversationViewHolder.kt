package com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview

import android.text.format.DateFormat
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.conversation.ConversationPreview
import kotlinx.android.synthetic.main.cardview_conversation.view.*
import java.text.SimpleDateFormat
import java.util.*

class ConversationViewHolder(
        itemView: View,
        private val clickListener: ConversationsRecyclerViewOnClickListener
) :
        RecyclerView.ViewHolder(itemView) {

    fun bindData(conversationPreview: ConversationPreview) {
        itemView.conversation_page_card_name.text = conversationPreview.title
        setLastMessageData(conversationPreview)

        if (conversationPreview.unreadMessagesCounter != 0) {
            setUnreadMessageMode(conversationPreview)
        } else {
            setWithoutUnreadMessagesMode()
        }
        itemView.setOnClickListener {
            clickListener.onConversationClick(conversationPreview.id)
        }
    }

    /**
     * Last message
     * */

    private fun setUnreadMessageMode(conversation: ConversationPreview) {
        itemView.conversation_page_main_layout.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.colorTransparentBlue))
        itemView.conversation_page_card_badge_block.visibility = View.VISIBLE
        itemView.conversation_page_card_badge_number.text = conversation.unreadMessagesCounter.toString()
    }

    private fun setWithoutUnreadMessagesMode() {
        itemView.conversation_page_main_layout
                .setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorWhite))
        itemView.conversation_page_card_badge_block.visibility = View.INVISIBLE
    }

    private fun setLastMessageData(conversation: ConversationPreview) {
        if (!conversation.isEmpty) {
            itemView.conversation_page_card_last_message_text.text = conversation.userMessage
            itemView.conversation_page_card_last_message_author.visibility = View.VISIBLE

            itemView.conversation_page_card_last_message_author.text = itemView.context
                    .getString(R.string.messenger_last_message_placeholder, conversation.userName)

            val formatForDateNow = SimpleDateFormat(DateFormat
                    .getBestDateTimePattern(Locale.getDefault(), "hh:mm aa"),
                    Locale.getDefault())

            itemView.conversation_page_card_last_message_time.visibility = View.VISIBLE
            itemView.conversation_page_card_last_message_time.text = formatForDateNow
                    .format(conversation.userMessageTime)

            if (conversation.userImageAddress != "") {
                Glide.with(itemView.context)
                        .load(conversation.userImageAddress)
                        .into(itemView.conversation_page_card_image)
            } else {
                Glide.with(itemView.context)
                        .load(R.drawable.ic_sentiment_satisfied_blue_24dp)
                        .into(itemView.conversation_page_card_image)
            }
        } else {
            itemView.conversation_page_card_last_message_text.text = itemView.context
                    .resources.getString(R.string.messenger_empty_conversation)
            itemView.conversation_page_card_last_message_author.visibility = View.GONE
            itemView.conversation_page_card_last_message_time.visibility = View.INVISIBLE
            Glide.with(itemView.context)
                    .load(R.drawable.ic_sentiment_satisfied_blue_24dp)
                    .into(itemView.conversation_page_card_image)
        }
    }

}
