package com.tydeya.familycircle.family.description;

import java.util.Calendar;

public enum ZodiacSign {

    ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN,
    AQUARiUS, PISCES;

    private static int[] lowerBorderDay = new int[]{21, 21, 21, 22, 23, 24, 24, 24, 23, 22, 21, 21};
    private static int[] upperBorderDay = new int[]{20, 20, 21, 22, 23, 23, 23, 22, 21, 20, 20, 20};
    private static int[] zodiacMonth = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 1};
    private static int birthDateDay;
    private static int birthDateMonth;


    public static ZodiacSign getZodiacSign(Calendar birthDate) {

        birthDateDay = birthDate.get(Calendar.DAY_OF_MONTH);
        birthDateMonth = birthDate.get(Calendar.MONTH);

        for (int i = 0; i < ZodiacSign.values().length; i++) {
            if (check(i)) {
                return ZodiacSign.values()[i];
            }
        }
        return null;
    }

    private static boolean check(int number) {
        return (zodiacMonth[number] == birthDateMonth && lowerBorderDay[number] <= birthDateDay
                || zodiacMonth[(number + 1) % 12] == birthDateMonth && birthDateDay <= upperBorderDay[number]);
    }
}