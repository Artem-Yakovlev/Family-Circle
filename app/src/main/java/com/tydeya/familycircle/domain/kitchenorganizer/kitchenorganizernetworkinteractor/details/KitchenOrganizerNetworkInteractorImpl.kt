package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.details.KitchenOrganizerInteractor
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class KitchenOrganizerNetworkInteractorImpl(
        private val callback: KitchenNetworkInteractorCallback
) :
        KitchenOrganizerNetworkInteractor {

    object Companion {
        fun convertServerDataToFood(documentSnapshot: DocumentSnapshot) = Food(
                documentSnapshot.get(FIRESTORE_FOOD_TITLE).toString(),
                documentSnapshot.get(FIRESTORE_FOOD_DESCRIPTION).toString(),
                when (documentSnapshot.getLong(FIRESTORE_FOOD_STATUS)) {
                    0L -> FoodStatus.NEED_BUY
                    else -> FoodStatus.IN_FRIDGE
                },
                documentSnapshot.getDouble(FIRESTORE_FOOD_CALORIES),
                documentSnapshot.getDouble(FIRESTORE_FOOD_PROTEIN),
                documentSnapshot.getDouble(FIRESTORE_FOOD_FATS)
        )
    }

    /**
     * Buy catalogs requires
     * */
    override fun requireKitchenBuyCatalogData() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .orderBy("date_of_create", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        val buyCatalogs = ArrayList<BuyCatalog>()
                        for (i in 0 until querySnapshot.documents.size) {
                            val document = querySnapshot.documents[i]

                            buyCatalogs.add(BuyCatalog(document.id,
                                    document.get(FIRESTORE_BUY_CATALOG_TITLE).toString(),
                                    document.getDate(FIRESTORE_BUY_CATALOG_DATE),
                                    ArrayList()
                            ))

                            fillBuyListFromServer(buyCatalogs[i])
                        }
                        withContext(Dispatchers.Main) {
                            callback.buyCatalogsAllDataUpdated(buyCatalogs)
                        }
                    }
                }
    }

    private fun fillBuyListFromServer(buyCatalog: BuyCatalog) {
        val task = FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(buyCatalog.id).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .orderBy(FIRESTORE_FOOD_TITLE, Query.Direction.ASCENDING)
                .get()
        val documentsSnapshot = Tasks.await(task)

        for (i in 0 until documentsSnapshot.documents.size) {
            buyCatalog.products
                    .add(Companion.convertServerDataToFood(documentsSnapshot.documents[i]))
        }
    }

    /**
     * Catalog
     * */

    override fun createBuyList(title: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION).add(
                hashMapOf(
                        FIRESTORE_BUY_CATALOG_TITLE to title,
                        FIRESTORE_BUY_CATALOG_DATE to Date()
                ) as Map<String, Any>)
    }

    override fun renameBuyList(catalogId: String, newTitle: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(catalogId).update(FIRESTORE_BUY_CATALOG_TITLE, newTitle)
    }

    override fun deleteBuyList(catalogId: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(catalogId).delete()
    }

    /**
     * Food in buy catalog
     * */

    override fun createProductInFirebase(id: String, title: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(id).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .add(createProductFromTitle(title, 0))
    }

    private fun createProductFromTitle(title: String, foodStatusNumber: Int) = hashMapOf(
            FIRESTORE_FOOD_TITLE to title,
            FIRESTORE_FOOD_DESCRIPTION to "",
            FIRESTORE_FOOD_STATUS to foodStatusNumber,
            FIRESTORE_FOOD_CALORIES to 0,
            FIRESTORE_FOOD_PROTEIN to 0,
            FIRESTORE_FOOD_FATS to 0
    ) as Map<String, Any>

    override fun deleteProductInFirebase(catalogId: String, title: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(catalogId).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .whereEqualTo(FIRESTORE_FOOD_TITLE, title).get()
                .addOnSuccessListener { querySnapshot ->
                    GlobalScope.launch(Dispatchers.Default) {
                        for (document in querySnapshot.documents) {
                            document.reference.delete()
                        }
                    }
                }
    }

    override fun buyProductFirebaseProcessing(catalogId: String, title: String) {

        val firestore = FirebaseFirestore.getInstance()

        firestore.collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(catalogId).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .whereEqualTo(FIRESTORE_FOOD_TITLE, title).get()
                .addOnSuccessListener { querySnapshot ->
                    GlobalScope.launch(Dispatchers.Default) {
                        // In FIRESTORE food_status 1 == FOOD_IN_FRIDGE
                        if (querySnapshot.documents.size != 0) {
                            querySnapshot.documents[0].reference.update(FIRESTORE_FOOD_STATUS, 1)
                        }
                    }
                }
        firestore.collection(FIRESTORE_FRIDGE_COLLECTION)
                .add(createProductFromTitle(title, 1))
    }

    override fun editProductInFirebase(id: String, actualTitle: String, newTitle: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(id).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .whereEqualTo(FIRESTORE_FOOD_TITLE, actualTitle).get()
                .addOnSuccessListener { querySnapshot ->
                    GlobalScope.launch(Dispatchers.Default) {
                        if (querySnapshot.documents.size != 0) {
                            querySnapshot.documents[0].reference.update(FIRESTORE_FOOD_TITLE, newTitle)
                        }
                    }
                }
    }

    /**
     * Fridge manager
     * */

    override fun requireFoodInFridgeData() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_FRIDGE_COLLECTION)
                .orderBy(FIRESTORE_FOOD_TITLE, Query.Direction.ASCENDING)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        val foodInFridge = ArrayList<Food>()
                        querySnapshot.documents.forEach {
                            foodInFridge.add(Companion.convertServerDataToFood(it))
                        }
                        withContext(Dispatchers.Main) {
                            callback.foodInFridgeDataUpdate(foodInFridge)
                        }
                    }
                }
    }

    /**
     * Food in fridge
     * */

    override fun addFoodInFridge(title: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_FRIDGE_COLLECTION)
                .add(createProductFromTitle(title, 1))
    }

    override fun deleteFoodFromFridge(title: String) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_FRIDGE_COLLECTION)
                .whereEqualTo(FIRESTORE_FOOD_TITLE, title).get()
                .addOnSuccessListener { querySnapshot ->
                    GlobalScope.launch(Dispatchers.Default) {
                        if (querySnapshot.documents.size != 0) {
                            querySnapshot.documents[0].reference.delete()
                        }
                    }
                }
    }
}

