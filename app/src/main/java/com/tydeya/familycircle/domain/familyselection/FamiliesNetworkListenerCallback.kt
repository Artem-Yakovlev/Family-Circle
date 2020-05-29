package com.tydeya.familycircle.domain.familyselection

import com.tydeya.familycircle.data.family.SimpleFamily
import com.tydeya.familycircle.utils.Resource

interface FamiliesNetworkListenerCallback {

    fun familiesInformationUpdated(families: Resource<List<SimpleFamily>>)

}
