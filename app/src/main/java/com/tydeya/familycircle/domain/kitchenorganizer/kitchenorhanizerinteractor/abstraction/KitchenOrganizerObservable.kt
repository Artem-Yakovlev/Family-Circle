package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorhanizerinteractor.abstraction


interface KitchenOrganizerObservable {

    fun subscribe(callback: KitchenOrganizerCallback)

    fun unsubscribe(callback: KitchenOrganizerCallback)
}