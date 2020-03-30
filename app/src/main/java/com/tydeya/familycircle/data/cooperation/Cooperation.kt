package com.tydeya.familycircle.data.cooperation

import java.util.*

data class Cooperation(val id: String, val userPhone: String, val item: String,
                       val type: CooperationType, val time: Date)