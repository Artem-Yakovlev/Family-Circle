package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.chatmessage.ChatMessage
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import javax.inject.Inject

const val OUTGOING_MESSAGE_VIEW_TYPE = 0
const val INBOX_MESSAGE_VIEW_TYPE = 1

class InConversationChatRecyclerViewAdapter(
        val context: Context,
        var messages: ArrayList<ChatMessage>
) : RecyclerView.Adapter<InConversationChatViewHolder>() {

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    private val userPhoneNumber = FirebaseAuth.getInstance().currentUser!!.phoneNumber

    init {
        App.getComponent().injectRecyclerViewAdapter(this)
    }

    /**
     * View holder create
     * */

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].authorPhoneNumber == userPhoneNumber) {
            OUTGOING_MESSAGE_VIEW_TYPE
        } else {
            INBOX_MESSAGE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InConversationChatViewHolder(
            LayoutInflater.from(context).inflate(
                    when (viewType) {
                        OUTGOING_MESSAGE_VIEW_TYPE -> R.layout.outgoing_message_card
                        else -> R.layout.message_card_received
                    },
                    parent, false), viewType)

    override fun onBindViewHolder(holder: InConversationChatViewHolder, position: Int) {
        val authorUser = FamilyAssistantImpl(familyInteractor.actualFamily)
                .getUserByPhone(messages[position].authorPhoneNumber)

        var authorUserName = context.resources.getString(R.string.unknown_text)
        var authorUserImageAddress = ""

        authorUser?.let {
            authorUserName = authorUser.description.name
            authorUserImageAddress = authorUser.description.imageAddress
        }

        holder.bindData(messages[position], authorUserName, authorUserImageAddress)
    }


    override fun getItemCount() = messages.size

    fun refreshData(messages: ArrayList<ChatMessage>) {
        this.messages = messages
        notifyDataSetChanged()
    }
}