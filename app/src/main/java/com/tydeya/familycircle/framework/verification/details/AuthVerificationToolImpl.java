package com.tydeya.familycircle.framework.verification.details;

import android.app.Activity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.framework.verification.abstraction.AuthVerificationCallback;
import com.tydeya.familycircle.framework.verification.abstraction.AuthVerificationTool;

import java.util.concurrent.TimeUnit;

public class AuthVerificationToolImpl implements AuthVerificationTool {

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallbacks;
    private String fullPhoneNumber;
    private AuthVerificationCallback callback;

    public AuthVerificationToolImpl(AuthVerificationCallback callback) {
        this.callback = callback;
        this.authCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                callback.onVerificationCompleted(phoneAuthCredential, fullPhoneNumber);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                callback.onVerificationFailed(e);
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                callback.onCodeSent(s, forceResendingToken);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }
        };
    }

    @Override
    public void verifyPhoneNumber(String fullPhoneNumber, Activity activity) {
        this.fullPhoneNumber = fullPhoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                fullPhoneNumber,
                60,
                TimeUnit.SECONDS,
                activity,
                authCallbacks);
    }
}
