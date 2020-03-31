package com.tydeya.familycircle.framework.verification.details;

import android.app.Activity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.framework.verification.abstraction.AuthResendSmsTool;

import java.util.concurrent.TimeUnit;

public class AuthResendSmsToolImpl implements AuthResendSmsTool {

    PhoneAuthProvider.OnVerificationStateChangedCallbacks resendCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                }
            };

    @Override
    public void resendSms(Activity activity, String fullPhoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                fullPhoneNumber,
                60,
                TimeUnit.SECONDS,
                activity,
                resendCallbacks);
    }
}
