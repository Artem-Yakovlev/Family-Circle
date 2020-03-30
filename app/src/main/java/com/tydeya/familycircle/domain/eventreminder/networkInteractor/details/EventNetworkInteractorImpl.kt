package com.tydeya.familycircle.domain.eventreminder.networkInteractor.details

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tydeya.familycircle.App
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.eventreminder.FamilyEvent
import com.tydeya.familycircle.data.eventreminder.FamilyEventPriority
import com.tydeya.familycircle.data.eventreminder.FamilyEventType
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractor
import com.tydeya.familycircle.domain.eventreminder.networkInteractor.abstraction.EventNetworkInteractorCallback
import com.tydeya.familycircle.domain.onlinemanager.details.OnlineInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class EventNetworkInteractorImpl(val callback: EventNetworkInteractorCallback) : EventNetworkInteractor {

    @Inject
    lateinit var onlineManager: OnlineInteractorImpl

    init {
        App.getComponent().injectNetworkInteractor(this)
    }

    override fun requireEventDataFromServer() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_EVENTS_COLLECTION)
                .orderBy(FIRESTORE_EVENTS_DATE, Query.Direction.ASCENDING)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {

                        onlineManager.registerUserActivity()

                        val familySingleEvents = ArrayList<FamilyEvent>()
                        val familyAnnualEvents = ArrayList<FamilyEvent>()

                        querySnapshot.documents.forEach {
                            val familyEvent = parseEventFromRawServerData(it)
                            if (familyEvent.type == FamilyEventType.ANNUAL_EVENT) {
                                familyAnnualEvents.add(familyEvent)
                            } else {
                                familySingleEvents.add(familyEvent)
                            }
                        }
                        withContext(Dispatchers.Main) {
                            callback.eventDataUpdate(familySingleEvents, familyAnnualEvents)
                        }
                    }
                }
    }

    override fun createEvent(familyEvent: FamilyEvent) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_EVENTS_COLLECTION)
                .add(parseEventForServer(familyEvent))
    }

    override fun editEvent(familyEvent: FamilyEvent) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_EVENTS_COLLECTION)
                .document(familyEvent.id).update(parseEventForServer(familyEvent))
    }

    /**
     * Data parsing
     * */

    private fun parseEventFromRawServerData(document: DocumentSnapshot) = FamilyEvent(
            document.id,
            document.getString(FIRESTORE_EVENTS_TITLE),
            document.getDate(FIRESTORE_EVENTS_DATE).time,
            document.getString(FIRESTORE_EVENTS_AUTHOR),
            document.getString(FIRESTORE_EVENTS_DESCRIPTION),
            when (document.getLong(FIRESTORE_EVENTS_PRIORITY)) {
                1L -> FamilyEventPriority.MIDDLE
                2L -> FamilyEventPriority.HIGH
                else -> FamilyEventPriority.LOW
            },
            when (document.getLong(FIRESTORE_EVENTS_TYPE)) {
                0L -> FamilyEventType.ROUTINE
                1L -> FamilyEventType.IMPORTANT_EVENT
                else -> FamilyEventType.ANNUAL_EVENT
            }
    )

    private fun parseEventForServer(familyEvent: FamilyEvent) = hashMapOf(
            FIRESTORE_EVENTS_TITLE to familyEvent.title,
            FIRESTORE_EVENTS_DATE to Date(familyEvent.timestamp),
            FIRESTORE_EVENTS_AUTHOR to FirebaseAuth.getInstance().currentUser!!.phoneNumber!!,
            FIRESTORE_EVENTS_DESCRIPTION to familyEvent.description,
            FIRESTORE_EVENTS_PRIORITY to familyEvent.priority.ordinal,
            FIRESTORE_EVENTS_TYPE to familyEvent.type.ordinal) as Map<String, Any>

}