package com.tydeya.familycircle;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static com.tydeya.familycircle.utils.StringExtensionsKt.ifNullToEmpty;

public class StringExtensionsTest {

    private final String notNullString = "wednesday";
    private final String emptyString = "";

    @Test
    public void ifNullToEmptyTest() {

        Assert.assertEquals(emptyString, ifNullToEmpty(null));

        Assert.assertEquals(notNullString, ifNullToEmpty(notNullString));
    }
}
