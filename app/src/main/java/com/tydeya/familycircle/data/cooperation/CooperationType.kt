package com.tydeya.familycircle.data.cooperation

enum class CooperationType {
    ADD_PRODUCT_IN_BUYLIST, CREATE_BUYLIST, BUY_PRODUCT, GIVE_TASK, REFUSE_TASK,
    PERFORM_TASK, CREATE_CONVERSATION;

    fun fromInt(number: Int) = when (number) {
        0 -> ADD_PRODUCT_IN_BUYLIST
        1 -> CREATE_BUYLIST
        2 -> BUY_PRODUCT
        3 -> GIVE_TASK
        4 -> REFUSE_TASK
        5 -> PERFORM_TASK
        else -> CREATE_CONVERSATION
    }
}
