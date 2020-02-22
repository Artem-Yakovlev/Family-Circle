package com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction;

public interface StartInputNumberView {

    void phoneNumberCountryCorrect();

    void phoneNumberCountryIncorrect();

    void verificationCodeSent(String userCodeId);

    void verificationFailure(Exception e);

    void verificationSuccess(boolean isAccountExist);
}
