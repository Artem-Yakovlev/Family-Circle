package com.tydeya.familycircle.domain.kitchenorganizer.foodinfridgeeventlistener

import com.google.firebase.firestore.*
import com.tydeya.familycircle.data.constants.FireStore
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.domain.kitchenorganizer.utils.convertServerDataToFood
import com.tydeya.familycircle.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodInFridgeEventListener(
        private val callback: FoodInFridgeEventListenerCallback
) :
        EventListener<QuerySnapshot>, EventListenerObservable {

    private val foodCollectionRef = FirebaseFirestore.getInstance()
            .collection(FireStore.FRIDGE_COLLECTION)
            .orderBy(FireStore.FOOD_TITLE, Query.Direction.ASCENDING)

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
                val foodInFridge = ArrayList<Food>()
                querySnapshot!!.documents.forEach {
                    foodInFridge.add(convertServerDataToFood(it))
                }
                withContext(Dispatchers.Main) {
                    callback.foodInFridgeProductsUpdated(Resource.Success(foodInFridge))
                }

            } else {
                withContext(Dispatchers.Main) {
                    callback.foodInFridgeProductsUpdated(Resource.Failure(exception))
                }
            }
        }
    }

}