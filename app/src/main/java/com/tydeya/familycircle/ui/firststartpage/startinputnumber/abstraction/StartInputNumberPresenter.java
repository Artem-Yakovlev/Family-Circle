package com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction;

public interface StartInputNumberPresenter {
    void onNextButtonClick(boolean isPhoneNumberCorrect);

    void verifyDialogPositiveButton(String fullPhoneNumber);
}
