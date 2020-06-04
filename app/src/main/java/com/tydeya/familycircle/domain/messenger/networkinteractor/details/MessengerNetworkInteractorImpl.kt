package com.tydeya.familycircle.domain.messenger.networkinteractor.details

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_COLLECTION
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_MEMBERS
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_MESSAGES
import com.tydeya.familycircle.data.constants.FireStore.CONVERSATION_TITLE
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_AUTHOR_PHONE
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_DATETIME
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_TEXT
import com.tydeya.familycircle.data.constants.FireStore.MESSAGE_UNREAD_PATTERN
import com.tydeya.familycircle.data.messenger.chatmessage.ChatMessage
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractor
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractorCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessengerNetworkInteractorImpl(val callback: MessengerNetworkInteractorCallback)
    :
        MessengerNetworkInteractor {

    @Suppress("UNCHECKED_CAST")
    override fun requireData() {
        FirebaseFirestore.getInstance().collection(CONVERSATION_COLLECTION)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        querySnapshot?.let {
                            val conversations = ArrayList<Conversation>()
                            for (document in querySnapshot.documents) {

                                val members = document.get(CONVERSATION_MEMBERS) as ArrayList<String>
                                if (FirebaseAuth.getInstance().currentUser!!.phoneNumber in members) {
                                    conversations.add(Conversation(
                                            document.id,
                                            document.getString(CONVERSATION_TITLE) ?: "",
                                            0,
                                            members, ArrayList()
                                    ))
                                }
                            }
                            callback.messengerConversationDataUpdated(conversations)
                        }
                    }

                }
    }

    override fun createConversation(title: String, members: ArrayList<String>) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(CONVERSATION_COLLECTION).add(
                    hashMapOf(
                            CONVERSATION_TITLE to title,
                            CONVERSATION_MEMBERS to members
                    ) as Map<String, Any>
            )
        }
    }

    override fun changeConversationMembers(conversationId: String, members: ArrayList<String>) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(CONVERSATION_COLLECTION)
                    .document(conversationId).update(hashMapOf(CONVERSATION_MEMBERS to members) as Map<String, Any>)
        }
    }

    override fun editConversationTitle(conversationId: String, title: String) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(CONVERSATION_COLLECTION)
                    .document(conversationId)
                    .update(hashMapOf(CONVERSATION_TITLE to title) as Map<String, Any>)
        }
    }

    override fun sendMessage(conversationId: String, message: ChatMessage, unreadByPhones: ArrayList<String>) {
        FirebaseFirestore.getInstance().collection(CONVERSATION_COLLECTION)
                .document(conversationId).collection(CONVERSATION_MESSAGES).add(
                        createMessageForFirebase(message, unreadByPhones)
                )
    }

    override fun readAllMessages(conversationId: String) {
        FirebaseFirestore.getInstance().collection(CONVERSATION_COLLECTION)
                .document(conversationId)
                .collection(CONVERSATION_MESSAGES).get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                    GlobalScope.launch(Dispatchers.Default) {
                        querySnapshot?.let { query ->
                            query.documents.forEach {
                                it.reference.update(
                                        hashMapOf(MESSAGE_UNREAD_PATTERN +
                                                "${FirebaseAuth.getInstance().currentUser!!.phoneNumber}"
                                                to false
                                        ) as Map<String, Any>)
                            }
                        }
                    }
                }
    }

    private fun createMessageForFirebase(message: ChatMessage, unreadByPhones: ArrayList<String>): Map<String, Any> {
        val firebaseMessageData = hashMapOf(
                MESSAGE_TEXT to message.text,
                MESSAGE_AUTHOR_PHONE to message.authorPhoneNumber,
                MESSAGE_DATETIME to message.dateTime
        ) as MutableMap<String, Any>
        unreadByPhones.forEach {
            firebaseMessageData["${MESSAGE_UNREAD_PATTERN}${it}"] = true
        }
        return firebaseMessageData
    }


}