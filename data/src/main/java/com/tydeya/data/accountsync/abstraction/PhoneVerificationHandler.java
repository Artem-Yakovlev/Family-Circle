package com.tydeya.data.accountsync.abstraction;

public interface PhoneVerificationHandler {
    void verifyPhone(PhoneVerificationResultRecipient recipient, String fullPhoneNumber);
}
