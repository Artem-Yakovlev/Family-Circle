package com.tydeya.familycircle.ui.conversationpart.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.ui.conversationpart.inconversation.InConversationActivity
import com.tydeya.familycircle.ui.conversationpart.main.createconversation.CreateConversationDialog
import com.tydeya.familycircle.ui.conversationpart.main.recyclerview.MainConversationRecyclerViewAdapter
import com.tydeya.familycircle.ui.conversationpart.main.recyclerview.MainConversationRecyclerViewOnClickListener
import kotlinx.android.synthetic.main.fragment_main_conversation_page.*
import javax.inject.Inject

class MainConversationPage
    :
        Fragment(R.layout.fragment_main_conversation_page),
        MessengerInteractorCallback,
        MainConversationRecyclerViewOnClickListener {

    @Inject
    lateinit var messengerInteractor: MessengerInteractor

    private lateinit var adapter: MainConversationRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.getComponent().injectFragment(this)
        setAdapter()
        setCreateButton()
    }

    private fun setAdapter() {
        adapter = MainConversationRecyclerViewAdapter(context!!, ArrayList(), this)
        main_conversation_page_recycler_view.layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        main_conversation_page_recycler_view.adapter = adapter

    }

    private fun setCreateButton() {
        main_conversation_toolbar_create_new_conversation.setOnClickListener {
            val dialog = CreateConversationDialog()
            dialog.show(parentFragmentManager, "createConversationDialog")
        }
    }

    private fun setCurrentData() {
        adapter.refreshData(messengerInteractor.conversations)
    }

    override fun onConversationClick(conversationId: String) {
        messengerInteractor.actualConversationId = conversationId
        startActivity(Intent(context, InConversationActivity::class.java))
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