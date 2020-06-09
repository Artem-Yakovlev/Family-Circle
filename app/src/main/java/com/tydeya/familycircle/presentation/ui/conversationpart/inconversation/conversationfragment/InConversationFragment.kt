package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.CONVERSATION_ID
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.presentation.MainActivity
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog.ConversationInfoDialogListener
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview.ChatRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.popBackStack
import com.tydeya.familycircle.utils.extensions.value
import kotlinx.android.synthetic.main.fragment_in_conversation.*

class InConversationFragment
    :
        Fragment(R.layout.fragment_in_conversation),
        MessengerInteractorCallback, ConversationInfoDialogListener {

    private lateinit var familyViewModel: FamilyViewModel
    private lateinit var chatAdapter: ChatRecyclerViewAdapter
    private lateinit var conversationId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString(CONVERSATION_ID)?.let {
            conversationId = it
        } ?: popBackStack()

        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        initAdapter()
        initSendButton()
        initInfoButton()
        initAddMemberButton()
    }

    private fun initAdapter() {
        chatAdapter = ChatRecyclerViewAdapter()
        conversation_chat_recycler_view.adapter = chatAdapter
        conversation_chat_recycler_view.layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    chatAdapter.refreshFamilyMembers(it.data)
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                }
            }
        })

    }

    private fun initSendButton() {
        chat_send_message_button.setOnClickListener {
            val messageText = chat_input_field.text.toString().trim()
            if (messageText != "") {
                MessengerInteractor.sendMessage(conversationId, messageText)
                chat_input_field.value = ""
            }
        }
    }

    /**
     * Toolbar buttons
     * */

    private fun initInfoButton() {
//        in_conversation_info_button.setOnClickListener {
//            val dialog = ConversationInfoDialog(messengerInteractor.actualConversationId, this)
//            dialog.show(parentFragmentManager, "conversation_info_dialog")
//        }
    }

    private fun initAddMemberButton() {
//        in_conversation_add_member_button.setOnClickListener {
//            val dialog = ConversationAddMemberDialog(messengerInteractor.actualConversationId)
//            dialog.show(parentFragmentManager, "conversation_add_member_dialog")
    }

    override fun messengerDataFromServerUpdated() {
        MessengerInteractor.conversationById(conversationId)?.let {
            in_conversation_toolbar.title = it.title
            chatAdapter.refreshChatMessages(it.messages)

        } ?: popBackStack()
    }


    override fun leaveConversation() {
//        messengerInteractor.leaveConversation(messengerInteractor.actualConversationId)
//        requireActivity().finish()
    }

    /**
     * Callbacks
     * */

    override fun onResume() {
        super.onResume()
        MessengerInteractor.subscribe(this)
        (requireActivity() as MainActivity).setBottomNavigationVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        MessengerInteractor.unsubscribe(this)
        (requireActivity() as MainActivity).setBottomNavigationVisibility(true)
    }

}
