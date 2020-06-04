package com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore.BUYS_CATALOG_FOODS
import com.tydeya.familycircle.data.constants.FireStore.KITCHEN_COLLECTION
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.domain.kitchenorganizer.utils.convertServerDataToFood
import com.tydeya.familycircle.utils.Resource
import com.tydeya.familycircle.utils.extensions.firestoreFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class KitchenBuyCatalogEventListener(
        val id: String,
        val familyId: String,
        val callback: BuyCatalogEventListenerCallback
) :
        EventListener<QuerySnapshot>, EventListenerObservable {

    private val foodCollectionRef = firestoreFamily(familyId)
            .collection(KITCHEN_COLLECTION)
            .document(id)
            .collection(BUYS_CATALOG_FOODS)

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

                val actualProducts = ArrayList<Food>()

                for (rawFood in querySnapshot!!.documents) {
                    actualProducts.add(convertServerDataToFood(rawFood))
                }

                withContext(Dispatchers.Main) {
                    callback.buyCatalogProductsUpdated(id, Resource.Success(actualProducts))
                }
            } else {
                withContext(Dispatchers.Main) {
                    callback.buyCatalogProductsUpdated(id, Resource.Failure(exception))
                }
            }
        }
    }

}