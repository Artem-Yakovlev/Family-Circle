package com.tydeya.familycircle.ui.conversationpart.inconversation.conversationfragment.conversationaddmemberdialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import kotlinx.android.synthetic.main.dialog_conversation_add_member.view.*
import javax.inject.Inject

class ConversationAddMemberDialog(private val conversationId: String
) :
        DialogFragment(), ConversationAddMemberDialogRecyclerViewClickListener {

    @Inject
    lateinit var messengerInteractor: MessengerInteractor

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    private val names = ArrayList<String>()
    private val phoneNumbers = ArrayList<String>()

    init {
        App.getComponent().injectDialog(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_conversation_add_member, null)


        setRecyclerView(view.dialog_conversation_add_member_recyclerview)

        view.dialog_conversation_add_member_cancel_button.setOnClickListener {
            dismiss()
        }

        builder.setView(view)
        return builder.create()
    }

    private fun setRecyclerView(recyclerView: RecyclerView) {
        messengerInteractor.conversationById(conversationId)?.let { conversation ->
            familyInteractor.actualFamily.familyMembers.forEach {
                if (!conversation.members.contains(it.fullPhoneNumber)) {
                    names.add(it.description.name)
                    phoneNumbers.add(it.fullPhoneNumber)
                }
            }
        }
        recyclerView.adapter =
                ConversationAddMemberDialogRecyclerViewAdapter(context!!, names, this)
        recyclerView.layoutManager =
                LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)


    }

    override fun onAddMemberButtonClick(position: Int) {
        messengerInteractor.addMemberInConversation(conversationId, phoneNumbers[position])
        dismiss()
    }
}