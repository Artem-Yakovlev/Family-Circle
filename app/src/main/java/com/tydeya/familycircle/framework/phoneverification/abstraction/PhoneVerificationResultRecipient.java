package com.tydeya.familycircle.framework.phoneverification.abstraction;

public interface PhoneVerificationResultRecipient {
    void onCodeSent(String userCodeId);

    void verificationSuccess(String fullPhoneNumber);

    void verificationFailure(Exception e);
}
