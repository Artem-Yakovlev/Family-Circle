package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.abstraction

import com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.abstraction.KitchenOrganizerCallback


interface KitchenOrganizerObservable {

    fun subscribe(callback: KitchenOrganizerCallback)

    fun unsubscribe(callback: KitchenOrganizerCallback)
}