package com.tydeya.familycircle.registrationlogin;

import com.google.android.material.textfield.TextInputEditText;
import com.tydeya.familycircle.R;

class DataConfirming {

    public static boolean isEmptyCheck(TextInputEditText textInputEditText){
        assert textInputEditText.getText() != null;

        if (textInputEditText.getText().toString().equals("")){
            textInputEditText.setError(textInputEditText.getContext()
                    .getResources()
                    .getString(R.string.empty_necessary_field_warning));
            return true;
        }
        return false;
    }
}
