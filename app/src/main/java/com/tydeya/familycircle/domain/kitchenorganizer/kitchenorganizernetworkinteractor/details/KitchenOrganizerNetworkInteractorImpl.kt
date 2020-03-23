package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.details

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenNetworkInteractorCallback
import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction.KitchenOrganizerNetworkInteractor
import java.util.*
import kotlin.collections.ArrayList

class KitchenOrganizerNetworkInteractorImpl(
        private val kitchenNetworkInteractorCallback: KitchenNetworkInteractorCallback
) :
        KitchenOrganizerNetworkInteractor {


    override fun requireKitchenBuyCatalogData() {
        val buyCatalogs = ArrayList<BuyCatalog>()

        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .addSnapshotListener { querySnapshot, _ ->
                    run {

                        for (index in 0 until querySnapshot.documents.size) {
                            val document = querySnapshot.documents[index]

                            buyCatalogs.add(BuyCatalog(document.id,
                                    document.get(FIRESTORE_BUY_CATALOG_TITLE).toString(),
                                    document.get(FIRESTORE_BUY_CATALOG_DATE) as Date,
                                    ArrayList()
                            ))

                            fillBuyListFromServer(buyCatalogs[index])
                        }


                    }
                }
    }

    private fun fillBuyListFromServer(buyCatalog: BuyCatalog) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_KITCHEN_COLLECTION)
                .document(buyCatalog.id).collection(FIRESTORE_BUY_CATALOG_FOODS)
                .get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                    {
                        if (task.isSuccessful) {
                            task.result.documents()
                        }
                    }
                }
    }

}