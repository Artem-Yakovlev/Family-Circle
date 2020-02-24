package com.tydeya.familycircle.framework.signInWithCode.details;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.tydeya.familycircle.framework.signInWithCode.abstraction.SignInWithPhoneCodeTool;
import com.tydeya.familycircle.framework.signInWithCode.abstraction.SignInWithPhoneCodeToolCallback;

public class SignInWithPhoneCodeToolImpl implements SignInWithPhoneCodeTool {

    private SignInWithPhoneCodeToolCallback callback;
    private FirebaseAuth auth;

    public SignInWithPhoneCodeToolImpl(SignInWithPhoneCodeToolCallback callback, FirebaseAuth auth) {
        this.callback = callback;
        this.auth = auth;
    }


    @Override
    public void signInWithCredentialCode(PhoneAuthCredential phoneAuthCredential, Activity activity) {
        auth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(activity, task -> {

                    if (task.isSuccessful()) {
                        callback.signInIsSuccessful();
                    } else {
                        callback.signInCodeIsInvalid();
                    }
                });
    }
}
