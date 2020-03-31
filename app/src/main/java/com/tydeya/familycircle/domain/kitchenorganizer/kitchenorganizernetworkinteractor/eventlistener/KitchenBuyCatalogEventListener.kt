package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.eventlistener

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_BUY_CATALOG_FOODS
import com.tydeya.familycircle.data.constants.Firebase.FIRESTORE_KITCHEN_COLLECTION
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details.KitchenOrganizerNetworkInteractorImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KitchenBuyCatalogEventListener(
        val id: String,
        val callback: KitchenNetworkInteractorCallback
) :
        EventListener<QuerySnapshot>, EventListenerObservable {

    private val foodCollectionRef = FirebaseFirestore.getInstance()
            .collection(FIRESTORE_KITCHEN_COLLECTION)
            .document(id)
            .collection(FIRESTORE_BUY_CATALOG_FOODS)

    private lateinit var registration: ListenerRegistration

    override fun register() {
        registration = foodCollectionRef.addSnapshotListener(this)
    }

    override fun unregister() {
        registration.remove()
    }

    override fun onEvent(querySnapshot: QuerySnapshot?, exception: FirebaseFirestoreException?) {
        GlobalScope.launch(Dispatchers.Default) {

            val actualProducts = ArrayList<Food>()

            for (rawFood in querySnapshot!!.documents) {
                actualProducts.add(KitchenOrganizerNetworkInteractorImpl
                        .Companion.convertServerDataToFood(rawFood))
            }

            withContext(Dispatchers.Main) {
                callback.buyCatalogDataUpdated(id, actualProducts)
            }
        }
    }
}