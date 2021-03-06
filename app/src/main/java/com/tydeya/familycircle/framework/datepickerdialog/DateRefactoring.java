package com.tydeya.familycircle.framework.datepickerdialog;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateRefactoring {

    public static String getDateLocaleText(Calendar calendar) {

        String DATE_TEXT_PATTERN_SKELETON = "yyyy-MM-dd";
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(),
                DATE_TEXT_PATTERN_SKELETON);

        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

    public static long dateToTimestamp(Date date) {
        if (date == null) {
            return -1;
        }
        return date.getTime();
    }

}
