package com.tydeya.familycircle.domain.verification.details

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.tydeya.familycircle.domain.verification.abstraction.AuthVerificationCallback
import com.tydeya.familycircle.domain.verification.abstraction.AuthVerificationTool
import java.util.concurrent.TimeUnit

class AuthVerificationToolImpl(private val callback: AuthVerificationCallback) : AuthVerificationTool {

    override fun verifyPhoneNumber(fullPhoneNumber: String, activity: Activity) {

        val authCallbacks = object : OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnSuccessListener {
                            callback.onVerificationCompleted(fullPhoneNumber)
                        }
                        .addOnFailureListener {
                            callback.onVerificationFailed(it)
                        }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                callback.onVerificationFailed(e)
            }

            override fun onCodeSent(s: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(s, forceResendingToken)
                callback.onCodeSent(s, forceResendingToken)
            }
        }

        PhoneAuthProvider.getInstance()
                .verifyPhoneNumber(fullPhoneNumber, 60, TimeUnit.SECONDS, activity, authCallbacks)
    }
}