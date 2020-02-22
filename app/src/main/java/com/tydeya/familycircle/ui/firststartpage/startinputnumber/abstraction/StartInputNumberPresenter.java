package com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction;

public interface StartInputNumberPresenter {
    void onAcceptButtonClick(boolean isPhoneNumberCorrect);

    void sendVerificationCode(String fullPhoneNumber);
}
