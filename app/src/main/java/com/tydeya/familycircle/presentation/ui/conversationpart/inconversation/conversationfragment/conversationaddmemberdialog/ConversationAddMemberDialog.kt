package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog

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
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.currentFamilyId
import com.tydeya.familycircle.utils.extensions.toArrayList
import kotlinx.android.synthetic.main.dialog_conversation_add_member.*

class ConversationAddMemberDialog
    :
        DialogFragment(), ConversationAddMemberDialogRecyclerViewClickListener {

    private lateinit var familyViewModel: FamilyViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        return inflater.inflate(R.layout.dialog_conversation_add_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        dialog_conversation_add_member_cancel_button.setOnClickListener {
            dismiss()
        }
    }

    private fun setRecyclerView() {

        val membersAdapter = ConversationAddMemberDialogRecyclerViewAdapter(listener = this)
        dialog_conversation_add_member_recyclerview.adapter = membersAdapter
        dialog_conversation_add_member_recyclerview.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)

        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer { familyMembers ->
            when (familyMembers) {
                is Resource.Success ->
                    requireArguments().getString(CONVERSATION_ID)?.let { conversationId ->

                        MessengerInteractor.conversationById(conversationId)?.let { conversation ->
                            membersAdapter.refreshData(familyMembers.data
                                    .filter { it.fullPhoneNumber !in conversation.members }
                                    .toArrayList())
                        } ?: dismiss()

                    } ?: dismiss()
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

    override fun onAddMemberButtonClick(phoneNumber: String) {
        requireArguments().getString(CONVERSATION_ID)?.let {
            MessengerInteractor.addMemberInConversation(it, phoneNumber)
        }
        dismiss()
    }

    companion object {

        val TAG = ConversationAddMemberDialog::class.java.simpleName

        private const val CONVERSATION_ID = "conversation_id"

        fun newInstance(conversationId: String) = ConversationAddMemberDialog().apply {
            arguments = bundleOf(CONVERSATION_ID to conversationId)
        }
    }
}
