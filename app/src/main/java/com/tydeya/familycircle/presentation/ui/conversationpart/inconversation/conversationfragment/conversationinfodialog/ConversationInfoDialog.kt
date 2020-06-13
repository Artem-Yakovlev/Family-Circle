package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.popBackStack
import com.tydeya.familycircle.utils.extensions.toArrayList
import com.tydeya.familycircle.utils.extensions.value
import kotlinx.android.synthetic.main.dialog_conversation_info.*

class ConversationInfoDialog(

) : DialogFragment(), MessengerInteractorCallback {

    private lateinit var familyViewModel: FamilyViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        return inflater.inflate(R.layout.dialog_conversation_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_conversation_info_cancel_button.setOnClickListener {
            dismiss()
        }

        requireArguments().getString(CONVERSATION_ID)?.let { conversationId ->

            MessengerInteractor.conversationById(conversationId)?.let {
                dialog_conversation_info_input.value = it.title
                setRecyclerViewAdapter(it)
            }

            dialog_conversation_info_save_button.setOnClickListener {
                if (!DataConfirming.isEmptyCheck(
                                dialog_conversation_info_input,
                                true
                        )
                ) {
                    MessengerInteractor.editConversationTitle(
                            conversationId = conversationId,
                            actualTitle = dialog_conversation_info_input.value.trim()
                    )
                    dismiss()
                }
            }

            dialog_conversation_info_leave_button.setOnClickListener {
                MessengerInteractor.leaveConversation(conversationId)
                dismiss()
                requireParentFragment().popBackStack()
            }

        } ?: dismiss()
    }

    private fun setRecyclerViewAdapter(conversation: Conversation) {
        val adapter = ConversationInfoRecyclerViewAdapter()
        dialog_conversation_info_recyclerview.adapter = adapter
        dialog_conversation_info_recyclerview.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
        )
        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer { familyMembers ->
            when (familyMembers) {
                is Resource.Success -> adapter.refreshData(familyMembers.data
                        .filter { it.fullPhoneNumber in conversation.members }
                        .toArrayList())
                is Resource.Failure -> dismiss()
            }
        })

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun messengerDataFromServerUpdated() {
        requireArguments().getString(CONVERSATION_ID)?.let {
            MessengerInteractor.conversationById(it)?.let { conversation ->
                setRecyclerViewAdapter(conversation)
            } ?: dismiss()
        } ?: dismiss()

    }

    override fun onResume() {
        super.onResume()
        MessengerInteractor.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        MessengerInteractor.unsubscribe(this)
    }

    companion object {

        val TAG = ConversationInfoDialog::class.java.simpleName

        private const val CONVERSATION_ID = "conversation_id"

        fun newInstance(conversationId: String) =
                ConversationInfoDialog().apply {
                    arguments = bundleOf(CONVERSATION_ID to conversationId)
                }
    }

}
