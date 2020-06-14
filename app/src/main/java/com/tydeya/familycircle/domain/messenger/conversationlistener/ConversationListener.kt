package com.tydeya.familycircle.domain.messenger.conversationlistener

import android.util.Log
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_MESSAGES
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_AUTHOR_PHONE
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_DATETIME
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_TEXT
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_UNREAD_PATTERN
import com.tydeya.familycircle.data.messenger.chat.ChatMessage
import com.tydeya.familycircle.utils.extensions.getUserPhone
import com.tydeya.familycircle.utils.extensions.toArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class ConversationListener(
        private val familyId: String,
        private val conversationId: String,
        private val callback: ConversationListenerCallback
) :
        EventListener<QuerySnapshot>, ConversationListenerObservable {

    private val conversationMessagesReference = FirebaseFirestore.getInstance()
            .collection(FAMILY_COLLECTION)
            .document(familyId)
            .collection(CONVERSATION_COLLECTION)
            .document(conversationId)
            .collection(CONVERSATION_MESSAGES)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = conversationMessagesReference.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, p1: FirebaseFirestoreException?) {
        GlobalScope.launch(Dispatchers.Default) {
            querySnapshot?.let { querySnapshot ->
                val messages = ArrayList<ChatMessage>()
                var unreadCounter = 0

                for (document in querySnapshot.documents) {
                    val whoSawMessage = ArrayList<String>()

                    val message = parseMessageFromServer(document)
                    if (!message.isViewed) {
                        unreadCounter++
                    }
                    messages.add(message)
                }

                withContext(Dispatchers.Main) {
                    callback.conversationMessagesUpdated(conversationId, messages, unreadCounter)
                }
            }

        }
    }

    private fun parseMessageFromServer(document: DocumentSnapshot) = ChatMessage(
            document.id,
            document.getString(MESSAGE_AUTHOR_PHONE) ?: "+0",
            document.getString(MESSAGE_TEXT) ?: "",
            document.getDate(MESSAGE_DATETIME) ?: Date(),
            !isMessageUnread(document),
            getMembersWhoSawMessage(document)
    )

    private fun getMembersWhoSawMessage(document: DocumentSnapshot) =
            (document.data?.keys ?: ArrayList<String>())
                    .filter { it.matches(UNREAD_BY_PHONE_REGEX) }
                    .filterNot { document.getBoolean(it) ?: false }
                    .map { it.subSequence(MESSAGE_UNREAD_PATTERN.length, it.length).toString() }
                    .toArrayList()


    private fun isMessageUnread(document: DocumentSnapshot) = document
            .getBoolean(MESSAGE_UNREAD_PATTERN + getUserPhone()) ?: false

    companion object {
        private val UNREAD_BY_PHONE_REGEX = "$MESSAGE_UNREAD_PATTERN[+0-9]+".toRegex()
    }

}