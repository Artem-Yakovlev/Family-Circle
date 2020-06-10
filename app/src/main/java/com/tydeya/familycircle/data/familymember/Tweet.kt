package com.tydeya.familycircle.data.familymember

import java.util.*

data class Tweet(
        val id: String = "",
        val authorPhone: String,
        val name: String = "",
        val text: String,
        val date: Date
)
