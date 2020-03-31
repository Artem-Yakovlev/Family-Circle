package com.tydeya.familycircle.data.eventmanager

data class FamilyEvent(var id: String, var title: String, var timestamp: Long,
                       var authorPhone: String, var description: String,
                       var priority: FamilyEventPriority, var type: FamilyEventType)