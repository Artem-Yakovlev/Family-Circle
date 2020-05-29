package com.tydeya.familycircle.presentation.ui.registrationpart.authorization.inputnumber.abstraction;

import android.app.Activity;

public interface StartInputNumberPresenter {

    void onClickNextButton(boolean isValidPhoneNumber);

    void verifyPhoneNumber(String fullPhoneNumber, Activity activity);
}
