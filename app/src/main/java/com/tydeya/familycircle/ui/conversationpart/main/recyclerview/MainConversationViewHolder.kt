package com.tydeya.familycircle.ui.conversationpart.main.recyclerview

import android.content.Context
import android.text.format.DateFormat
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import kotlinx.android.synthetic.main.cardview_conversation.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainConversationViewHolder(itemView: View,
                                 private val clickListener: MainConversationRecyclerViewOnClickListener
) :
        RecyclerView.ViewHolder(itemView) {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    init {
        App.getComponent().injectViewHolder(this)
    }

    fun bindData(context: Context, conversation: Conversation) {
        itemView.conversation_page_card_name.text = conversation.title

        setLastMessageData(context, conversation)

        if (conversation.unreadMessagesCounter != 0) {
            setUnreadMessageMode(conversation)
        } else {
            setWithoutUnreadMessagesMode(conversation)
        }

        itemView.setOnClickListener {
            clickListener.onConversationClick(conversation.id)
        }
    }

    /**
     * Last message
     * */

    private fun setUnreadMessageMode(conversation: Conversation) {
        itemView.conversation_page_main_layout
                .setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorTransparentBlue))

        itemView.conversation_page_card_badge_block.visibility = View.VISIBLE
        itemView.conversation_page_card_badge_number.text = conversation.unreadMessagesCounter.toString()
    }

    private fun setWithoutUnreadMessagesMode(conversation: Conversation) {
        itemView.conversation_page_main_layout
                .setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorWhite))

        itemView.conversation_page_card_badge_block.visibility = View.INVISIBLE
    }

    private fun setLastMessageData(context: Context, conversation: Conversation) {
        if (conversation.messages.isNotEmpty()) {

            val lastMessage = conversation.messages[conversation.messages.size - 1]
            itemView.conversation_page_card_last_message_text.text = lastMessage.text

            val lastMessageAuthor = getLastMessageAuthor(lastMessage.authorPhoneNumber)
            itemView.conversation_page_card_last_message_author.visibility = View.VISIBLE

            itemView.conversation_page_card_last_message_author.text = if (lastMessageAuthor != null) {
                "${lastMessageAuthor.description.name}: "
            } else {
                "${context.resources.getString(R.string.unknown_text)}: "
            }

            val pattern: String = DateFormat.getBestDateTimePattern(Locale.getDefault(), "hh:mm aa")
            val formatForDateNow = SimpleDateFormat(pattern, Locale.getDefault())
            itemView.conversation_page_card_last_message_time.visibility = View.VISIBLE
            itemView.conversation_page_card_last_message_time.text = formatForDateNow.format(lastMessage.dateTime)

            if (lastMessageAuthor.description.imageAddress != "") {
                Glide.with(itemView.context)
                        .load(lastMessageAuthor.description.imageAddress)
                        .into(itemView.conversation_page_card_image)
            } else {
                Glide.with(itemView.context)
                        .load(R.drawable.ic_sentiment_satisfied_blue_24dp)
                        .into(itemView.conversation_page_card_image)
            }
        } else {

            itemView.conversation_page_card_last_message_text.text = context.resources.getString(R.string.messenger_empty_conversation)
            itemView.conversation_page_card_last_message_author.visibility = View.GONE
            itemView.conversation_page_card_last_message_time.visibility = View.INVISIBLE
            Glide.with(itemView.context)
                    .load(R.drawable.ic_sentiment_satisfied_blue_24dp)
                    .into(itemView.conversation_page_card_image)
        }
    }

    private fun getLastMessageAuthor(phoneNumber: String) =
            FamilyAssistantImpl(familyInteractor.actualFamily).getUserByPhone(phoneNumber)

}