package com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview.CreateConversationMembersCheckBoxListener
import com.tydeya.familycircle.presentation.ui.conversationpart.main.createconversation.recyclerview.CreateConversationMembersRecyclerViewAdapter
import kotlinx.android.synthetic.main.dialog_create_conversation.view.*
import javax.inject.Inject

class CreateConversationDialog : DialogFragment(), CreateConversationMembersCheckBoxListener {

    @Inject
    lateinit var messengerInteractor: MessengerInteractor

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    private val names = ArrayList<String>()
    private val phoneNumbers = ArrayList<String>()
    private val needToAdd = ArrayList<Boolean>()

    init {
        App.getComponent().injectDialog(this)
        familyInteractor.actualFamily.familyMembers.forEach {
            if (it.fullPhoneNumber != FirebaseAuth.getInstance().currentUser!!.phoneNumber) {
                names.add(it.description.name)
                phoneNumbers.add(it.fullPhoneNumber)
                needToAdd.add(false)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_create_conversation, null)

        setAdapter(view.dialog_create_conversation_recyclerview)

        view.dialog_create_conversation_create_button.setOnClickListener {
            if (!DataConfirming.isEmptyNecessaryCheck(view.dialog_create_conversation_title, true)) {
                if (needToAdd.contains(true)) {

                    messengerInteractor.createConversation(view.dialog_create_conversation_title
                            .text.toString().trim(), getConversationMembers())
                    dismiss()

                } else {
                    Toast.makeText(requireContext(), getString(R.string.messenger_create_conversation_dialog_add_someone_text),
                            Toast.LENGTH_LONG).show()
                }
            }
        }

        view.dialog_create_conversation_cancel_button.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }

    private fun setAdapter(recyclerView: RecyclerView) {
        val adapter = CreateConversationMembersRecyclerViewAdapter(requireContext(),
                familyInteractor.actualFamily
                        .getFamilyMemberExceptUserPhone(
                                FirebaseAuth.getInstance().currentUser?.phoneNumber ?: ""),
                this)

        recyclerView.adapter = adapter

        recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun checkboxChangeState(phoneNumber: String, actualState: Boolean) {
        needToAdd[phoneNumbers.indexOf(phoneNumber)] = actualState
    }

    private fun getConversationMembers(): ArrayList<String> {
        val members = ArrayList<String>()
        members.add(0, FirebaseAuth.getInstance().currentUser!!.phoneNumber!!)

        for (i in 0 until needToAdd.size) {
            if (needToAdd[i]) {
                members.add(phoneNumbers[i])
            }
        }

        return members
    }
}