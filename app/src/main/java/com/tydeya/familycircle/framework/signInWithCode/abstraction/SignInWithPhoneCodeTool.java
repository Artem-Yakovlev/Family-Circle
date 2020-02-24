package com.tydeya.familycircle.framework.signInWithCode.abstraction;

import android.app.Activity;

import com.google.firebase.auth.PhoneAuthCredential;

public interface SignInWithPhoneCodeTool {

    void signInWithCredentialCode(PhoneAuthCredential phoneAuthCredential, Activity activity);
}
