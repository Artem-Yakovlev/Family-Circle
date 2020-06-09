package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
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
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog.ConversationAddMemberDialog
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog.ConversationInfoDialog
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview.ChatRecyclerViewAdapter
import com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.recyclerview.InConversationViewHolderListener
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_in_conversation.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InConversationFragment
    :
        Fragment(R.layout.fragment_in_conversation),
        MessengerInteractorCallback,
        InConversationViewHolderListener {

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
        chatAdapter = ChatRecyclerViewAdapter(listener = this)
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
            if (chatAdapter.itemCount != 0) {
                conversation_chat_recycler_view
                        .smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    /**
     * Toolbar buttons
     * */

    private fun initInfoButton() {
        in_conversation_info_button.setOnClickListener {
            ConversationInfoDialog.newInstance(conversationId)
                    .show(parentFragmentManager, ConversationInfoDialog.TAG)
        }
    }

    private fun initAddMemberButton() {
        in_conversation_add_member_button.setOnClickListener {

            MessengerInteractor.conversationById(conversationId)?.let { conversation ->

                val familyMembers = familyViewModel.familyMembers.value

                if (familyMembers is Resource.Success
                        && familyMembers.data.size > conversation.members.size) {

                    ConversationAddMemberDialog.newInstance(conversationId)
                            .show(parentFragmentManager, ConversationAddMemberDialog.TAG)
                } else {
                    requireContext().showToast(R.string.messenger_full_conversation)
                }

            } ?: popBackStack()

        }
    }

    override fun messengerDataFromServerUpdated() {
        MessengerInteractor.conversationById(conversationId)?.let {
            in_conversation_toolbar.title = it.title
            chatAdapter.refreshChatMessages(it.messages)
            if (it.messages.isNotEmpty() && it.messages.last().authorPhoneNumber == getUserPhone()) {
                conversation_chat_recycler_view.smoothScrollToPosition(chatAdapter.itemCount - 1)
            }
            MessengerInteractor.readAllMessages(conversationId)

        } ?: popBackStack()
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

    override fun showMessageEditingMenu(messageId: String, view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.inflate(R.menu.edit_message_menu)

        popupMenu.setOnMenuItemClickListener {
            if (it.itemId == R.id.delete_message) {
                GlobalScope.launch {
                    MessengerInteractor.deleteMessage(conversationId, messageId)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

}
