package com.tydeya.familycircle.ui.conversationpart.main.createconversation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.ui.conversationpart.main.createconversation.recyclerview.CreateConversationMembersCheckBoxListener
import com.tydeya.familycircle.ui.conversationpart.main.createconversation.recyclerview.CreateConversationMembersRecyclerViewAdapter
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

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_create_conversation, null)

        setAdapter(view.dialog_create_conversation_recyclerview)

        view.dialog_create_conversation_create_button.setOnClickListener {
            if (!DataConfirming.isEmptyNecessaryCheck(view.dialog_create_conversation_title, true)) {
                if (needToAdd.contains(true)) {
                    for (i in 0 until names.size) {
                        Log.d("ASMR", "${names[i]} ${phoneNumbers[i]} ${needToAdd[i]}")
                    }
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
        val adapter = CreateConversationMembersRecyclerViewAdapter(context!!,
                familyInteractor.actualFamily
                        .getFamilyMemberExceptUserPhone(
                                FirebaseAuth.getInstance().currentUser!!.phoneNumber), this)

        recyclerView.adapter = adapter

        recyclerView.layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
    }

    override fun checkboxChangeState(phoneNumber: String, actualState: Boolean) {
        needToAdd[phoneNumbers.indexOf(phoneNumber)] = actualState
    }
}