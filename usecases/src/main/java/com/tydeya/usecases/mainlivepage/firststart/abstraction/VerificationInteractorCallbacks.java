package com.tydeya.usecases.mainlivepage.firststart.abstraction;

public interface VerificationInteractorCallbacks {

    void onCodeSent(String userCodeId);

    void verificationSuccess(String fullPhoneNumber);

    void verificationFailure(Exception e);
}
