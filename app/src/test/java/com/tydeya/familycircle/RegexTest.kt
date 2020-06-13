package com.tydeya.familycircle

import com.tydeya.familycircle.data.constants.FireStore
import org.junit.Assert
import org.junit.Test

class RegexTest {

    @Test
    fun unread_by_regex_test() {
        val regex = "${FireStore.MESSAGE_UNREAD_PATTERN}[+0-9]+".toRegex()
        Assert.assertTrue("unread_by_+79056644712".matches(regex))
    }

}