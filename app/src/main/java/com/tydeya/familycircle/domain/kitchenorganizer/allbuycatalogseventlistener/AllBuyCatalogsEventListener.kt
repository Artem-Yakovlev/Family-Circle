package com.tydeya.familycircle.domain.kitchenorganizer.allbuycatalogseventlistener

import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_BUYS_CATALOG_DATE
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_BUYS_CATALOG_NUMBER_PRODUCTS
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_BUYS_CATALOG_NUMBER_PURCHASED
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_BUYS_CATALOG_TITLE
import com.tydeya.familycircle.data.constants.FireStore.FIRESTORE_KITCHEN_COLLECTION
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class AllBuyCatalogsEventListener(private val callback: AllBuyCatalogsEventListenerCallback)
    : EventListener<QuerySnapshot>, EventListenerObservable {

    private val foodCollectionRef = FirebaseFirestore.getInstance()
            .collection(FIRESTORE_KITCHEN_COLLECTION)
            .orderBy(FIRESTORE_BUYS_CATALOG_DATE, Query.Direction.DESCENDING)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = foodCollectionRef.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
        GlobalScope.launch(Dispatchers.Default) {

            if (exception == null) {
                val buyCatalogs = ArrayList<BuyCatalog>()

                if (querySnapshot != null) {

                    for (i in 0 until querySnapshot.documents.size) {
                        val document = querySnapshot.documents[i]

                        buyCatalogs.add(BuyCatalog(document.id,
                                document.get(FIRESTORE_BUYS_CATALOG_TITLE).toString(),
                                document.getDate(FIRESTORE_BUYS_CATALOG_DATE) ?: Date(),
                                document.getLong(FIRESTORE_BUYS_CATALOG_NUMBER_PRODUCTS) ?: 0,
                                document.getLong(FIRESTORE_BUYS_CATALOG_NUMBER_PURCHASED) ?: 0
                        ))
                    }

                    withContext(Dispatchers.Main) {
                        callback.allBuyCatalogsUpdated(Resource.Success(buyCatalogs))
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback.allBuyCatalogsUpdated(Resource.Failure(exception))
                }
            }
        }
    }

}