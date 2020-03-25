package com.tydeya.familycircle.domain.kitchenorganizer.kitchenorganizernetworkinteractor.abstraction

import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationInteractorCallback


interface KitchenOrganizerObservable {

    fun subscribe(callback: KitchenOrganizerCallback)

    fun unsubscribe(callback: KitchenOrganizerCallback)
}