package com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.conversationsassistant.details.ConversationsAssistantImpl
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.ui.conversationpart.chatpart.MessagingActivity
import com.tydeya.familycircle.ui.conversationpart.chatpart.correspondence.details.ChatRecyclerViewAdapter
import com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.recyclerview.InConversationChatRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_in_conversation.*
import javax.inject.Inject

class InConversationFragment : Fragment(R.layout.fragment_in_conversation), MessengerInteractorCallback {

    @Inject
    lateinit var messengerInteractor: MessengerInteractor

    private lateinit var adapter: InConversationChatRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        setAdapter()
        setCurrentData()
    }

    private fun setAdapter() {
        adapter = InConversationChatRecyclerViewAdapter(context!!, ArrayList())

        conversation_chat_recycler_view.adapter = adapter
        conversation_chat_recycler_view.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setCurrentData() {
        adapter.refreshData(messengerInteractor
                .conversationById(messengerInteractor.actualConversationId)!!.messages)
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
