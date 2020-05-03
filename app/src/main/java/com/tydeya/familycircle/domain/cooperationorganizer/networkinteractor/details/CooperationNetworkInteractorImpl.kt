package com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.details

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tydeya.familycircle.data.constants.Firebase.*
import com.tydeya.familycircle.data.cooperation.Cooperation
import com.tydeya.familycircle.data.cooperation.CooperationType
import com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.abstraction.CooperationNetworkInteractor
import com.tydeya.familycircle.domain.cooperationorganizer.networkinteractor.abstraction.CooperationNetworkInteractorCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class CooperationNetworkInteractorImpl(
        val callback: CooperationNetworkInteractorCallback

) :
        CooperationNetworkInteractor {

    /**
     * Listen data changes
     * */

    override fun listenCooperationData() {
        FirebaseFirestore.getInstance().collection(FIRESTORE_COOPERATION_COLLECTION)
                .orderBy(FIRESTORE_COOPERATION_TIME, Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, _ ->
                    GlobalScope.launch(Dispatchers.Default) {
                        querySnapshot?.let {
                            val cooperationData = ArrayList<Cooperation>()

                            for (document in querySnapshot.documents) {
                                cooperationData.add(createCooperationDataFromRawData(document))
                            }

                            withContext(Dispatchers.Main) {
                                callback.cooperationDataFromServerUpdate(cooperationData)
                            }
                        }
                    }
                }
    }

    private fun createCooperationDataFromRawData(document: DocumentSnapshot): Cooperation {
        return Cooperation(document.id,
                document.getString(FIRESTORE_COOPERATION_AUTHOR) ?: "+0",
                document.getString(FIRESTORE_COOPERATION_ITEM) ?: "",
                CooperationType.values()[(document.getLong(FIRESTORE_COOPERATION_TYPE)
                        ?: 0).toInt()],
                document.getDate(FIRESTORE_COOPERATION_TIME) ?: Date())
    }

    /**
     * Edit data
     * */

    override fun registerCooperation(cooperation: Cooperation) {
        FirebaseFirestore.getInstance().collection(FIRESTORE_COOPERATION_COLLECTION).add(
                hashMapOf(
                        FIRESTORE_COOPERATION_AUTHOR to cooperation.userPhone,
                        FIRESTORE_COOPERATION_ITEM to cooperation.item,
                        FIRESTORE_COOPERATION_TYPE to cooperation.type.ordinal,
                        FIRESTORE_COOPERATION_TIME to cooperation.time) as Map<String, Any>
        )
    }
}