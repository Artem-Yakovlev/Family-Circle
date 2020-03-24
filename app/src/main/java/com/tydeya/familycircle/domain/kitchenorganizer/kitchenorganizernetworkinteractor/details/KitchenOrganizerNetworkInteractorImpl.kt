package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.data.kitchenorganizer.food.Food
import com.tydeya.familycircle.data.kitchenorganizer.food.FoodStatus
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class KitchenOrganizerNetworkInteractorImpl(
        private val callback: KitchenNetworkInteractorCallback
) :
        KitchenOrganizerNetworkInteractor {


    override fun requireKitchenBuyCatalogData() {


        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .addSnapshotListener() { querySnapshot, _ ->
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

    private suspend fun fillBuyListFromServer(buyCatalog: BuyCatalog) {
        val task = FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(buyCatalog.id).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .get()
        val documentsSnapshot = Tasks.await(task)

        for (i in 0 until documentsSnapshot.documents.size) {
            buyCatalog.products
                    .add(convertServerDataToFood(documentsSnapshot.documents[i]))
        }
    }

    private fun convertServerDataToFood(documentSnapshot: DocumentSnapshot) = Food(
            documentSnapshot.get(FIRESTORE_FOOD_TITLE).toString(),
            documentSnapshot.get(FIRESTORE_FOOD_DESCRIPTION).toString(),
            when (documentSnapshot.get(FIRESTORE_FOOD_STATUS)) {
                0 -> FoodStatus.NEED_BUY
                else -> FoodStatus.IN_FRIDGE
            },
            documentSnapshot.getDouble(FIRESTORE_FOOD_CALORIES),
            documentSnapshot.getDouble(FIRESTORE_FOOD_PROTEIN),
            documentSnapshot.getDouble(FIRESTORE_FOOD_FATS)
    )


    override fun setUpdateKitchenDataListener(buyCatalogs: ArrayList<BuyCatalog>) {

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

    override fun setUpdateCatalogDataListener(buyCatalog: BuyCatalog) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(buyCatalog.id).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        Log.d("ASMR", "update")
                        val actualBuyCatalog = BuyCatalog(buyCatalog.id,
                                buyCatalog.title, buyCatalog.dateOfCreate, ArrayList())

                        for (rawFood in querySnapshot.documents) {
                            actualBuyCatalog.products.add(convertServerDataToFood(rawFood))
                        }

                        withContext(Dispatchers.Main) {
                            callback.buyCatalogDataUpdated(actualBuyCatalog)
                        }
                    }
                }
    }

    override fun removeUpdateCatalogDataListener(id: String) {
        GlobalScope.launch(Dispatchers.Default) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                    .document(id).collection(FIRESTORE_BUY_CATALOG_FOODS)
                    .addSnapshotListener { _, _ -> }.remove()

        }
    }


}