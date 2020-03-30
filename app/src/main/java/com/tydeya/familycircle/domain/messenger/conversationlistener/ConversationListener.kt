package com.tydeya.familycircle.domain.messenger.conversationlistener

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.chatmessage.ChatMessage
import com.tydeya.familycircle.data.constants.Firebase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConversationListener(private val conversationId: String,
                           private val callback: ConversationListenerCallback
) :
        EventListener<QuerySnapshot>, ConversationListenerObservable {


    private val conversationMessagesReference = FirebaseFirestore.getInstance()
            .collection(FIRESTORE_CONVERSATION_COLLECTION)
            .document(conversationId)
            .collection(FIRESTORE_CONVERSATION_MESSAGES)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = conversationMessagesReference.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, p1: FirebaseFirestoreException?) {
        GlobalScope.launch(Dispatchers.Default) {
            Log.d("ASMR", "onEvent")
            val messages = ArrayList<ChatMessage>()

            for (document in querySnapshot!!.documents) {
                messages.add(parseMessageFromServer(document))
            }
            withContext(Dispatchers.Main) {
                callback.conversationMessagesUpdated(conversationId, messages)
            }

        }
    }

    private fun parseMessageFromServer(document: DocumentSnapshot) = ChatMessage(
            document.getString(FIRESTORE_MESSAGE_AUTHOR_PHONE),
            document.getString(FIRESTORE_MESSAGE_TEXT),
            document.getDate(FIRESTORE_MESSAGE_DATETIME),
            !isMessageUnread(document)

    )

    private fun isMessageUnread(document: DocumentSnapshot) = document.getBoolean(
            FIRESTORE_MESSAGE_UNREAD_PATTERN + FirebaseAuth.getInstance().currentUser!!.phoneNumber)
            ?: false


}