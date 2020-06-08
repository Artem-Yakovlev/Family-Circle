package com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.ConversationMember
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview.CreateConversationMembersRecyclerViewAdapter
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModel
import com.tydeya.familycircle.presentation.viewmodel.familyviewmodel.FamilyViewModelFactory
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.*
import kotlinx.android.synthetic.main.dialog_create_conversation.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateConversationDialog : DialogFragment() {

    private lateinit var familyViewModel: FamilyViewModel
    private lateinit var recyclerAdapter: CreateConversationMembersRecyclerViewAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        familyViewModel = ViewModelProviders
                .of(requireActivity(), FamilyViewModelFactory(requireActivity().currentFamilyId))
                .get(FamilyViewModel::class.java)

        return inflater.inflate(R.layout.dialog_create_conversation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        dialog_create_conversation_create_button.setOnClickListener {
            if (dialog_create_conversation_title.isNotEmptyCheck(true)) {
                GlobalScope.launch(Dispatchers.Default) {

                    val conversationMembers = recyclerAdapter.members
                            .filter(ConversationMember::isChecked)
                            .map(ConversationMember::phoneNumber)
                            .toArrayList()

                    withContext(Dispatchers.Main) {
                        if (conversationMembers.isNotEmpty()) {
                            MessengerInteractor.createConversation(
                                    dialog_create_conversation_title.text.toString(),
                                    conversationMembers.apply { add(getUserPhone()) }
                            )
                            dismiss()
                        } else {
                            requireContext().showToast(
                                    R.string.messenger_create_conversation_dialog_add_someone_text
                            )
                        }
                    }
                }
            }
        }

        dialog_create_conversation_cancel_button.setOnClickListener {
            dismiss()
        }
    }

    private fun initAdapter() {
        this.recyclerAdapter = CreateConversationMembersRecyclerViewAdapter(ArrayList())
        with(dialog_create_conversation_recyclerview) {
            this.adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext(),
                    LinearLayoutManager.VERTICAL, false)
        }

        familyViewModel.familyMembers.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch(Dispatchers.Default) {
                when (it) {
                    is Resource.Success -> {
                        val members = it.data
                                .filter { it.fullPhoneNumber != getUserPhone() }
                                .map(::ConversationMember)
                                .toArrayList()

                        withContext(Dispatchers.Main) {
                            recyclerAdapter.refreshData(members)
                        }
                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Failure -> {
                        withContext(Dispatchers.Main) {
                            dismiss()
                        }
                    }
                }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    companion object {

        private val TAG = CreateConversationDialog::class.java.simpleName

        fun newInstance() = CreateConversationDialog()
    }
}