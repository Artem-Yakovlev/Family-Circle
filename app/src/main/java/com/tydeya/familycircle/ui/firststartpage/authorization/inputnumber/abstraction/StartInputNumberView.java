package com.tydeya.familycircle.ui.firststartpage.authorization.inputnumber.abstraction;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthProvider;

public interface StartInputNumberView {

    void validPhoneNumber();

    void invalidPhoneNumber();

    void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken);

    void accountExist();

    void accountNotExistButAuthIsSuccess();

    void verificationFailed(FirebaseException e);
}
