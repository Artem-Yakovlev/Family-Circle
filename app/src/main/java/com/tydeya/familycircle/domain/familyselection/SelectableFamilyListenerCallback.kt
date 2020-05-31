package com.tydeya.familycircle.domain.familyselection

import com.tydeya.familycircle.data.family.FamilyDTO
import com.tydeya.familycircle.utils.Resource

interface SelectableFamilyListenerCallback {

    fun selectableFamiliesUpdated(
            selectableFamilies: Resource<List<FamilyDTO>>,
            inviteCodes: Resource<ArrayList<String>>
    )

}
