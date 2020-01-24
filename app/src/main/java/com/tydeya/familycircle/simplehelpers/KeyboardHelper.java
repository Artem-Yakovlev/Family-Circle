package com.tydeya.familycircle.simplehelpers;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardHelper {

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null && inputManager != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
