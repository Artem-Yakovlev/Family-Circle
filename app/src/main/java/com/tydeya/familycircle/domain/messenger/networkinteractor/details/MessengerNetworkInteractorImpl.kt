package com.tydeya.familycircle.domain.messenger.networkinteractor.details

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.messenger.conversation.Conversation
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorObservable
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

        FirebaseFirestore.getInstance().collection(FIRESTORE_CONVERSATION_COLLECTION)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        val conversations = ArrayList<Conversation>()
                        for (document in querySnapshot.documents) {

                            val members = document.get(FIRESTORE_CONVERSATION_MEMBERS) as ArrayList<String>
                            if (FirebaseAuth.getInstance().currentUser!!.phoneNumber in members) {
                                conversations.add(Conversation(
                                        document.id, document.getString(FIRESTORE_CONVERSATION_TITLE),
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


}