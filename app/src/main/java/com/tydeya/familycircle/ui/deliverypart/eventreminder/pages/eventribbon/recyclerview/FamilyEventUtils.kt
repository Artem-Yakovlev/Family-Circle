package com.tydeya.familycircle.ui.deliverypart.eventreminder.pages.eventribbon.recyclerview

import com.tydeya.familycircle.data.eventreminder.FamilyEvent

fun List<FamilyEvent>.toEventRibbonItems() : List<EventRibbonItem>{
    return this.map {text ->
        EventRibbonItem(text)
    }
}