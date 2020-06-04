package com.tydeya.familycircle.presentation.viewmodel.kitchen.allcatalogs

import androidx.lifecycle.MutableLiveData
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.allbuycatalogseventlistener.AllBuyCatalogsEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.allbuycatalogseventlistener.AllBuyCatalogsEventListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.utils.createBuysCatalogInFirebase
import com.tydeya.familycircle.domain.kitchenorganizer.utils.deleteBuyCatalogInFirebase
import com.tydeya.familycircle.domain.kitchenorganizer.utils.editBuysCatalogTitle
import com.tydeya.familycircle.presentation.viewmodel.base.FirestoreViewModel
import com.tydeya.familycircle.utils.Resource

class AllBuyCatalogsViewModel(
        private val familyId: String
) :
        FirestoreViewModel(), AllBuyCatalogsEventListenerCallback {

    private val allBuyCatalogsEventListener: AllBuyCatalogsEventListener =
            AllBuyCatalogsEventListener(familyId, this)

    val buyCatalogsResource: MutableLiveData<Resource<ArrayList<BuyCatalog>>> =
            MutableLiveData(Resource.Loading())

    init {
        allBuyCatalogsEventListener.register()
    }

    /**
     * Data retrieval
     * */

    fun getBuysCatalogTitleById(desiredId: String): Resource<String> {
        val actualResource = buyCatalogsResource.value

        if (actualResource is Resource.Success) {
            for (catalog in actualResource.data) {
                if (catalog.id == desiredId) {
                    return Resource.Success(catalog.title)
                }
            }
        }
        return Resource.Failure(IllegalArgumentException("Unknown id"))
    }

    /**
     * Data interaction
     * */

    fun isThereBuysCatalogWithName(name: String): Boolean {
        val actualResource = buyCatalogsResource.value
        if (actualResource is Resource.Success) {
            for (buysCatalog in actualResource.data) {
                if (buysCatalog.title == name) {
                    return true
                }
            }
            return false
        }
        return true
    }

    fun createBuysCatalog(title: String) {
        createBuysCatalogInFirebase(familyId, title)
    }

    fun editCatalogName(catalogId: String, newTitle: String) {
        editBuysCatalogTitle(familyId, catalogId, newTitle)
    }

    fun deleteCatalog(catalogId: String) {
        deleteBuyCatalogInFirebase(familyId, catalogId)
    }

    /**
     * Callback
     * */

    override fun allBuyCatalogsUpdated(buyCatalogsResourse: Resource<ArrayList<BuyCatalog>>) {
        this.buyCatalogsResource.value = buyCatalogsResourse
    }

    override fun onCleared() {
        super.onCleared()
        allBuyCatalogsEventListener.unregister()
    }

}