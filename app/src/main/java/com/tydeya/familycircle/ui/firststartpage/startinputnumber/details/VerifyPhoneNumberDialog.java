package com.tydeya.familycircle.ui.firststartpage.startinputnumber.details;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.tydeya.familycircle.R;

class VerifyPhoneNumberDialog {

    private VerifyPhoneNumberDialog(){}

    static AlertDialog create(Context context, String fullPhoneNumber,
                              DialogInterface.OnClickListener positiveButtonListener,
                              DialogInterface.OnClickListener negativeButtonListener) {

        StringBuilder questionText = new StringBuilder(context.getResources()
                .getString(R.string.first_start_input_number_correct_text));

        questionText.append(" ").append(fullPhoneNumber).append(" ?");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle(context.getResources().getString(R.string.first_start_input_number_correct_title));
        alertDialogBuilder.setMessage(questionText);

        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.first_start_input_number_correct_yes),
                positiveButtonListener);
        alertDialogBuilder.setNegativeButton(context.getResources().getString(R.string.first_start_input_number_correct_no),
                negativeButtonListener);

        return alertDialogBuilder.create();

    }
}
