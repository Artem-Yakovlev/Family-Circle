package com.tydeya.familycircle.domain.messenger.networkinteractor.details

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tydeya.familycircle.App
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.messenger.chatmessage.ChatMessage
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractor
import com.tydeya.familycircle.domain.messenger.networkinteractor.abstraction.MessengerNetworkInteractorCallback
import com.tydeya.familycircle.domain.onlinemanager.details.OnlineInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessengerNetworkInteractorImpl(val callback: MessengerNetworkInteractorCallback)
    :
        MessengerNetworkInteractor {

    @Inject
    lateinit var onlineManager: OnlineInteractorImpl

    init {
        App.getComponent().injectInteractor(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun requireData() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        onlineManager.registerUserActivity()
                        val conversations = ArrayList<Conversation>()
                        for (document in querySnapshot.documents) {

                            val members = document.get(FIRESTORE_CONVERSATION_MEMBERS) as ArrayList<String>
                            if (FirebaseAuth.getInstance().currentUser!!.phoneNumber in members) {
                                conversations.add(Conversation(
                                        document.id, document.getString(FIRESTORE_CONVERSATION_TITLE), 0,
                                        members, ArrayList()
                                ))
                            }
                        }
                        callback.messengerConversationDataUpdated(conversations)
                    }

                }
    }

    override fun createConversation(title: String, members: ArrayList<String>) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION).add(
                    hashMapOf(
                            FIRESTORE_CONVERSATION_TITLE to title,
                            FIRESTORE_CONVERSATION_MEMBERS to members
                    ) as Map<String, Any>
            )
        }
    }

    override fun changeConversationMembers(conversationId: String, members: ArrayList<String>) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                    .document(conversationId).update(hashMapOf(FIRESTORE_CONVERSATION_MEMBERS to members) as Map<String, Any>)
        }
    }

    override fun editConversationTitle(conversationId: String, title: String) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                    .document(conversationId)
                    .update(hashMapOf(FIRESTORE_CONVERSATION_TITLE to title) as Map<String, Any>)
        }
    }

    override fun sendMessage(conversationId: String, message: ChatMessage, unreadByPhones: ArrayList<String>) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                .document(conversationId).collection(FIRESTORE_CONVERSATION_MESSAGES).add(
                        createMessageForFirebase(message, unreadByPhones)
                )
    }

    override fun readAllMessages(conversationId: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                .document(conversationId)
                .collection(FIRESTORE_CONVERSATION_MESSAGES).get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot? ->
                    GlobalScope.launch(Dispatchers.Default) {
                        querySnapshot?.let { query ->
                            query.documents.forEach {
                                it.reference.update(
                                        hashMapOf(FIRESTORE_MESSAGE_UNREAD_PATTERN +
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
                FIRESTORE_MESSAGE_TEXT to message.text,
                FIRESTORE_MESSAGE_AUTHOR_PHONE to message.authorPhoneNumber,
                FIRESTORE_MESSAGE_DATETIME to message.dateTime
        ) as MutableMap<String, Any>
        unreadByPhones.forEach {
            firebaseMessageData["${FIRESTORE_MESSAGE_UNREAD_PATTERN}${it}"] = true
        }
        return firebaseMessageData
    }


}