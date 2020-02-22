package com.tydeya.familycircle.framework.phoneverification.abstraction;

public interface PhoneVerificationHandler {
    void verifyPhone(PhoneVerificationResultRecipient recipient, String fullPhoneNumber);
}
