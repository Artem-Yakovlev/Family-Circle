package com.tydeya.familycircle.domain.verification.abstraction;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public interface AuthVerificationCallback {

    void onVerificationCompleted(String fullPhoneNumber);

    void onVerificationFailed(Exception e);

    void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken);
}
