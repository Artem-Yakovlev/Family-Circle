package com.tydeya.familycircle.framework.verification.abstraction;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public interface AuthVerificationCallback {

    void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential, String fullPhoneNumber);

    void onVerificationFailed(FirebaseException e);

    void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken);
}
