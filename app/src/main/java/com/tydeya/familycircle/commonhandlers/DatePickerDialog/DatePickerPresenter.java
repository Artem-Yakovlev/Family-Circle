package com.tydeya.familycircle.commonhandlers.DatePickerDialog;

import android.app.DatePickerDialog;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Date;

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

        int limitYear = datePickerUpperLimit.get(Calendar.YEAR);
        int limitMonth = datePickerUpperLimit.get(Calendar.MONTH);
        int limitDay = datePickerUpperLimit.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener =
                (view1, year, monthOfYear, dayOfMonth) -> {
                    datePickerUsable.get().dateChanged(year, monthOfYear, dayOfMonth);
                };

        DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), dateSetListener,
                limitYear, limitMonth, limitDay);

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }
}
