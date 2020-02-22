package com.tydeya.familycircle.framework.phoneverification.details;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.framework.phoneverification.abstraction.PhoneVerificationHandler;
import com.tydeya.familycircle.framework.phoneverification.abstraction.PhoneVerificationResultRecipient;

import java.util.concurrent.TimeUnit;

public class PhoneVerificationHandlerImpl implements PhoneVerificationHandler {

    @Override
    public void verifyPhone(PhoneVerificationResultRecipient recipient, String fullPhoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                fullPhoneNumber,
                60,
                TimeUnit.SECONDS,
                runnable -> {
                }, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        recipient.verificationSuccess(fullPhoneNumber);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        recipient.verificationFailure(e);
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        recipient.onCodeSent(s);
                    }
                });
    }
}
