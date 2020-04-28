package com.tydeya.familycircle.domain.kitchenorganizer.allbuycatalogseventlistener

import com.tydeya.familycircle.data.kitchenorganizer.buylist.BuyCatalog
import com.tydeya.familycircle.utils.Resource

interface AllBuyCatalogsEventListenerCallback {

    fun allBuyCatalogsUpdated(buyCatalogsResourse: Resource<ArrayList<BuyCatalog>>)

}