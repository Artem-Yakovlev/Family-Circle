package com.tydeya.familycircle.data.eventreminder

data class FamilyEvent(var id: String, var title: String, var timestamp: Long,
                       var authorPhone: String, var priority: FamilyEventPriority,
                       var type: FamilyEventType)