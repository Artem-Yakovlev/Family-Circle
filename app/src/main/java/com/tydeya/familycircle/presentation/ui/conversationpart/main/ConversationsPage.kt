package com.tydeya.familycircle.presentation.ui.conversationpart.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.InConversationActivity
import com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.CreateConversationDialog
import com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview.ConversationsRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.conversationpart.main.recyclerview.ConversationsRecyclerViewOnClickListener
import kotlinx.android.synthetic.main.fragment_main_conversation_page.*

class ConversationsPage
    :
        Fragment(R.layout.fragment_main_conversation_page),
        MessengerInteractorCallback,
        ConversationsRecyclerViewOnClickListener {

    private lateinit var adapter: ConversationsRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCreateButton()
    }

    private fun initAdapter() {
        adapter = ConversationsRecyclerViewAdapter(ArrayList(), this)
        main_conversation_page_recycler_view.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        main_conversation_page_recycler_view.adapter = adapter

    }

    private fun initCreateButton() {
        main_conversation_toolbar_create_new_conversation.setOnClickListener {
            val dialog = CreateConversationDialog()
            dialog.show(parentFragmentManager, "createConversationDialog")
        }
    }

    override fun onConversationClick(conversationId: String) {
//        messengerInteractor.actualConversationId = conversationId
        startActivity(Intent(context, InConversationActivity::class.java))
    }

    /**
     * Callbacks
     * */

    override fun messengerDataFromServerUpdated() {
        adapter.refreshData(MessengerInteractor.conversations)
    }

    override fun onResume() {
        super.onResume()
        MessengerInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        MessengerInteractor.unsubscribe(this)
    }
}
