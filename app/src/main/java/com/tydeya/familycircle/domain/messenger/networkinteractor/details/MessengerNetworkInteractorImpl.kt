package com.tydeya.familycircle.domain.messenger.networkinteractor.details

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_MEMBERS
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_MESSAGES
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_TITLE
import com.tydeya.familycircle.data.constants.FireStore.FAMILY_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_AUTHOR_PHONE
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_DATETIME
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_TEXT
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_UNREAD_PATTERN
import com.tydeya.familycircle.data.messenger.chat.ChatMessage
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractor
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractorCallback
import com.tydeya.familycircle.utils.extensions.getUserPhone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessengerNetworkInteractorImpl(
        private val familyId: String,
        private val callback: MessengerNetworkInteractorCallback
) :
        MessengerNetworkInteractor {

    private val conversationsPath = FirebaseFirestore.getInstance()
            .collection(FAMILY_COLLECTION)
            .document(familyId)
            .collection(CONVERSATION_COLLECTION)

    private lateinit var conversationsRegistration: ListenerRegistration

    @Suppress("UNCHECKED_CAST")
    override fun connect() {
        conversationsRegistration = conversationsPath.addSnapshotListener { querySnapshot, _ ->
            GlobalScope.launch(Dispatchers.Default) {
                querySnapshot?.let {
                    val conversations = ArrayList<Conversation>()
                    for (document in querySnapshot.documents) {
                        val members = document.get(CONVERSATION_MEMBERS) as ArrayList<String>
                        if (getUserPhone() in members) {
                            conversations.add(Conversation(
                                    id = document.id,
                                    title = document.getString(CONVERSATION_TITLE) ?: "",
                                    members = members
                            ))
                        }
                    }
                    callback.messengerConversationDataUpdated(conversations)
                }
            }
        }
    }

    override fun disconnect() {
        conversationsRegistration.remove()
    }

    override fun createConversation(title: String, members: ArrayList<String>) {
        GlobalScope.launch(Dispatchers.Default) {
            conversationsPath.add(mutableMapOf<String, Any>(
                    CONVERSATION_TITLE to title,
                    CONVERSATION_MEMBERS to members
            ))
        }
    }

    override fun changeConversationMembers(conversationId: String, members: ArrayList<String>) {
        GlobalScope.launch(Dispatchers.Default) {
            conversationsPath.document(conversationId)
                    .update(mutableMapOf<String, Any>(CONVERSATION_MEMBERS to members))
        }
    }

    override fun editConversationTitle(conversationId: String, title: String) {
        GlobalScope.launch(Dispatchers.Default) {
            conversationsPath.document(conversationId)
                    .update(mutableMapOf<String, Any>(CONVERSATION_TITLE to title))
        }
    }

    override fun sendMessage(
            conversationId: String,
            message: ChatMessage,
            unreadByPhones: ArrayList<String>
    ) {
        conversationsPath.document(conversationId).collection(CONVERSATION_MESSAGES).add(
                createMessageForFirebase(message, unreadByPhones)
        )
    }

    override fun readAllMessages(conversationId: String) {
        conversationsPath
                .document(conversationId)
                .collection(CONVERSATION_MESSAGES).get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                    GlobalScope.launch(Dispatchers.Default) {
                        querySnapshot?.let { query ->
                            query.documents.forEach {
                                it.reference.update(mutableMapOf<String, Any>(
                                        "$MESSAGE_UNREAD_PATTERN ${getUserPhone()}" to false
                                ))
                            }
                        }
                    }
                }
    }

    private fun createMessageForFirebase(
            message: ChatMessage,
            unreadByPhones: ArrayList<String>
    ):
            Map<String, Any> {

        val firebaseMessageData = mutableMapOf<String, Any>(
                MESSAGE_TEXT to message.text,
                MESSAGE_AUTHOR_PHONE to message.authorPhoneNumber,
                MESSAGE_DATETIME to message.dateTime
        )
        unreadByPhones.forEach {
            firebaseMessageData["${MESSAGE_UNREAD_PATTERN}${it}"] = true
        }
        return firebaseMessageData
    }
}
