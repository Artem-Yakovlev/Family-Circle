package com.tydeya.familycircle.firststart;

import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.tydeya.familycircle.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * This datePickerListener is using in create new account
 * Return in textView date in LocaleFormat
 *
 * @author Artem Yakovlev
 * */
public class DatePickerOnclickListener implements View.OnClickListener {

    private TextView textView;

    DatePickerOnclickListener(TextView textView){
        this.textView = textView;
    }

    @Override
    public void onClick(View view) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener = (view1, year, monthOfYear, dayOfMonth) -> {
            Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            String pattern = DateFormat
                    .getBestDateTimePattern(Locale.getDefault(), "yyyy-MM-dd");

            SimpleDateFormat format  = new SimpleDateFormat(pattern, Locale.getDefault());
            String output = format.format(calendar.getTime());
            textView.setText(output);
            textView.setTextColor(view1.getContext().getResources().getColor(R.color.colorPrimary));
        };


        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), dateSetListener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }
}
