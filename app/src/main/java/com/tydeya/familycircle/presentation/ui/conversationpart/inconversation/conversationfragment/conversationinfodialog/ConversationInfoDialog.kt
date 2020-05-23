package com.tydeya.familycircle.presentation.ui.conversationpart.inconversation.conversationfragment.conversationinfodialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.familyassistant.details.FamilyAssistantImpl
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.framework.simplehelpers.DataConfirming
import com.tydeya.familycircle.utils.value
import kotlinx.android.synthetic.main.dialog_conversation_info.view.*
import javax.inject.Inject

class ConversationInfoDialog(private val conversationId: String,
                             private val listener: ConversationInfoDialogListener

) : DialogFragment() {

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

        val view = activity!!.layoutInflater.inflate(R.layout.dialog_conversation_info, null)

        messengerInteractor.conversationById(conversationId)?.let {
            view.dialog_conversation_info_input.value = it.title
            setRecyclerViewAdapter(view.dialog_conversation_info_recyclerview, it)
        }

        view.dialog_conversation_info_leave_button.setOnClickListener {
            listener.leaveConversation()
            dismiss()
        }

        view.dialog_conversation_info_cancel_button.setOnClickListener {
            dismiss()
        }

        view.dialog_conversation_info_save_button.setOnClickListener {
            if (!DataConfirming.isEmptyNecessaryCheck(view.dialog_conversation_info_input, true)) {
                messengerInteractor.editConversationTitle(conversationId,
                        view.dialog_conversation_info_input.text.toString().trim())
                dismiss()
            }
        }

        builder.setView(view)
        return builder.create()
    }

    private fun setRecyclerViewAdapter(recyclerView: RecyclerView, conversation: Conversation) {
        names.clear()
        val familyAssistant = FamilyAssistantImpl(familyInteractor.actualFamily)
        conversation.members.forEach {
            names.add(familyAssistant.getUserByPhone(it)!!.description.name)
            phoneNumbers.add(it)
        }
        val adapter = ConversationInfoRecyclerViewAdapter(context!!, names)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)

    }
}