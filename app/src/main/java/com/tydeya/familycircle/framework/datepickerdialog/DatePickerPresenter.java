package com.tydeya.familycircle.framework.datepickerdialog;

import android.app.DatePickerDialog;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerPresenter implements View.OnClickListener {

    private WeakReference<DatePickerUsable> datePickerUsable;
    private Calendar datePickerUpperLimit;

    public DatePickerPresenter(WeakReference<DatePickerUsable> datePickerUsable,
                               Calendar datePickerUpperLimit) {

        this.datePickerUsable = datePickerUsable;
        this.datePickerUpperLimit = datePickerUpperLimit;
    }


    @Override
    public void onClick(View view) {

        Calendar calendar = new GregorianCalendar();

        DatePickerDialog.OnDateSetListener dateSetListener =
                (view1, year, monthOfYear, dayOfMonth) -> {
                    datePickerUsable.get().dateChanged(year, monthOfYear, dayOfMonth);
                };

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), dateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(datePickerUpperLimit.getTimeInMillis());
        datePickerDialog.show();
    }
}
