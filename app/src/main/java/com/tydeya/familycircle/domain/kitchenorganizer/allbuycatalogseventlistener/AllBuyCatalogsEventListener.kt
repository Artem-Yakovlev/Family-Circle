package com.tydeya.familycircle.domain.kitchenorganizer.allbuycatalogseventlistener

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllBuyCatalogsEventListener(private val callback: AllBuyCatalogsEventListenerCallback)
    : EventListener<QuerySnapshot>, EventListenerObservable {

    private val foodCollectionRef = FirebaseFirestore.getInstance()
            .collection(FIRESTORE_KITCHEN_COLLECTION)
            .orderBy(FIRESTORE_BUY_CATALOG_DATE, Query.Direction.DESCENDING)

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
                                document.get(FIRESTORE_BUY_CATALOG_TITLE).toString(),
                                document.getDate(FIRESTORE_BUY_CATALOG_DATE)
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