package com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog.ConversationAddMemberDialog
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog.ConversationInfoDialog
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog.ConversationInfoDialogListener
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.recyclerview.InConversationChatRecyclerViewAdapter
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.fragment_in_conversation.*
import javax.inject.Inject

class InConversationFragment
    :
        Fragment(R.layout.fragment_in_conversation),
        MessengerInteractorCallback, ConversationInfoDialogListener {

    @Inject
    lateinit var messengerInteractor: MessengerInteractor

    private lateinit var adapter: InConversationChatRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        setAdapter()
        setSendButton()
        setInfoButton()
        setAddMemberButton()
        setCurrentData()
    }

    private fun setAdapter() {
        adapter = InConversationChatRecyclerViewAdapter(context!!, ArrayList())

        conversation_chat_recycler_view.adapter = adapter
        conversation_chat_recycler_view.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setCurrentData() {
        messengerInteractor.readAllMessages(messengerInteractor.actualConversationId)
        val conversation = messengerInteractor.conversationById(messengerInteractor.actualConversationId)!!

        in_conversation_toolbar.title = conversation.title

        adapter.refreshData(conversation.messages)
    }

    private fun setSendButton() {
        chat_send_message_button.setOnClickListener {
            val messageText = chat_input_field.text.toString().trim()
            if (messageText != "") {
                messengerInteractor.sendMessage(messengerInteractor.actualConversationId, messageText)
                chat_input_field.value = ""
            }
        }
    }

    /**
     * Toolbar buttons
     * */

    private fun setInfoButton() {
        in_conversation_info_button.setOnClickListener {
            val dialog = ConversationInfoDialog(messengerInteractor.actualConversationId, this)
            dialog.show(parentFragmentManager, "conversation_info_dialog")
        }
    }

    private fun setAddMemberButton() {
        in_conversation_add_member_button.setOnClickListener {
            val dialog = ConversationAddMemberDialog(messengerInteractor.actualConversationId)
            dialog.show(parentFragmentManager, "conversation_add_member_dialog")
        }
    }

    override fun leaveConversation() {
        messengerInteractor.leaveConversation(messengerInteractor.actualConversationId)
        activity!!.finish()
    }

    /**
     * Callbacks
     * */

    override fun messengerDataFromServerUpdated() {
        setCurrentData()
    }

    override fun onResume() {
        super.onResume()
        messengerInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        messengerInteractor.unsubscribe(this)
    }

}
