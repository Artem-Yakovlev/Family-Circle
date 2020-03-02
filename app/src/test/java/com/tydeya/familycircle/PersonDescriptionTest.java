package com.tydeya.familycircle;

import com.tydeya.familycircle.domain.familymember.ZodiacSign;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PersonDescriptionTest {

    private final String ORIGINAL_DESCRIPTION_TEXT = "It is really beautiful man!";
    private final String MODIFIED_DESCRIPTION_TEXT = "It is really strange man!";

    @Test
    public void zodiacSignTest() {

        Calendar calendar = new GregorianCalendar(2002, Calendar.MARCH, 21);
        Assert.assertEquals(ZodiacSign.ARIES, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1993, Calendar.APRIL, 23);
        Assert.assertEquals(ZodiacSign.TAURUS, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.MAY, 30);
        Assert.assertEquals(ZodiacSign.GEMINI, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.JUNE, 27);
        Assert.assertEquals(ZodiacSign.CANCER, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.AUGUST, 23);
        Assert.assertEquals(ZodiacSign.LEO, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.SEPTEMBER, 1);
        Assert.assertEquals(ZodiacSign.VIRGO, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.SEPTEMBER, 30);
        Assert.assertEquals(ZodiacSign.LIBRA, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.OCTOBER, 24);
        Assert.assertEquals(ZodiacSign.SCORPIO, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1940, Calendar.DECEMBER, 21);
        Assert.assertEquals(ZodiacSign.SAGITTARIUS, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.JANUARY, 0);
        Assert.assertEquals(ZodiacSign.CAPRICORN, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(2001, Calendar.JANUARY, 28);
        Assert.assertEquals(ZodiacSign.AQUARiUS, ZodiacSign.getZodiacSign(calendar));

        calendar = new GregorianCalendar(1970, Calendar.MARCH, 20);
        Assert.assertEquals(ZodiacSign.PISCES, ZodiacSign.getZodiacSign(calendar));
    }

}
