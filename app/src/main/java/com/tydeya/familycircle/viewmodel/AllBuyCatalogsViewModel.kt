package com.tydeya.familycircle.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.domain.kitchenorganizer.allbuycatalogseventlistener.AllBuyCatalogsEventListener
import com.tydeya.familycircle.domain.kitchenorganizer.allbuycatalogseventlistener.AllBuyCatalogsEventListenerCallback
import com.tydeya.familycircle.domain.kitchenorganizer.utils.EventListenerObservable
import com.tydeya.familycircle.domain.kitchenorganizer.buycatalogeventlistener.KitchenBuyCatalogEventListener
import com.tydeya.familycircle.utils.Resource

class AllBuyCatalogsViewModel : ViewModel(), AllBuyCatalogsEventListenerCallback {

    private val allBuyCatalogsEventListener: AllBuyCatalogsEventListener =
            AllBuyCatalogsEventListener(this)

    val buyCatalogsResource: MutableLiveData<Resource<ArrayList<BuyCatalog>>> =
            MutableLiveData(Resource.Loading())

    init {
        allBuyCatalogsEventListener.register()
    }

    override fun allBuyCatalogsUpdated(buyCatalogsResourse: Resource<ArrayList<BuyCatalog>>) {
        this.buyCatalogsResource.value = buyCatalogsResourse
    }

    override fun onCleared() {
        super.onCleared()
        allBuyCatalogsEventListener.unregister()
    }

}