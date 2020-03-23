package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details

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
import kotlin.collections.ArrayList

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

    /*
    override fun setUpdateKitchenDataListener(buyCatalogs: ArrayList<BuyCatalog>) {

        for (i in 0 until buyCatalogs.size) {
            FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                    .document(buyCatalogs[i].id)
                    .collection(FIRESTORE_BUY_CATALOG_FOODS)
                    .addSnapshotListener { querySnapshot, _ ->
                        GlobalScope.launch(Dispatchers.Default) {
                            if (querySnapshot.documents.size != buyCatalogs[i].products.size) {

                                val buyCatalog = BuyCatalog(
                                        buyCatalogs[i].id,
                                        buyCatalogs[i].title,
                                        buyCatalogs[i].dateOfCreate,
                                        ArrayList()
                                )

                                val products = ArrayList<Food>()

                                for (rawFood in querySnapshot.documents) {
                                    products.add(convertServerDataToFood(rawFood))
                                }
                                buyCatalog.products = products
                                withContext(Dispatchers.Main) {
                                    callback.buyCatalogDataUpdated(buyCatalog)
                                }
                            }
                        }
                    }
        }
    }
    */

    override fun setUpdateKitchenDataListener(buyCatalogs: ArrayList<BuyCatalog>) {

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


}