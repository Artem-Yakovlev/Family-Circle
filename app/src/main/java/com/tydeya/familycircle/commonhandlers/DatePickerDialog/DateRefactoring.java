package com.tydeya.familycircle.commonhandlers.DatePickerDialog;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateRefactoring {

    public static String getDateLocaleText(Calendar calendar) {

        String DATE_TEXT_PATTERN_SKELETON = "yyyy-MM-dd";
        String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(),
                DATE_TEXT_PATTERN_SKELETON);

        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(calendar.getTime());
    }

}
