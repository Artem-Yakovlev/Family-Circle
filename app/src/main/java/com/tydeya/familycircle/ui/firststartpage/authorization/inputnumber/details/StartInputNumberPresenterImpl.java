package com.tydeya.familycircle.ui.firststartpage.authorization.inputnumber.details;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.QuerySnapshot;
import com.tydeya.familycircle.framework.accountsync.abstraction.AccountExistingCheckUp;
import com.tydeya.familycircle.framework.accountsync.abstraction.AccountExistingCheckUpCallback;
import com.tydeya.familycircle.framework.accountsync.details.AccountExistingCheckUpImpl;
import com.tydeya.familycircle.framework.verification.abstraction.AuthVerificationCallback;
import com.tydeya.familycircle.framework.verification.abstraction.AuthVerificationTool;
import com.tydeya.familycircle.framework.verification.details.AuthVerificationToolImpl;
import com.tydeya.familycircle.ui.firststartpage.authorization.inputnumber.abstraction.StartInputNumberPresenter;
import com.tydeya.familycircle.ui.firststartpage.authorization.inputnumber.abstraction.StartInputNumberView;

import java.lang.ref.WeakReference;

public class StartInputNumberPresenterImpl implements StartInputNumberPresenter,
        AuthVerificationCallback, AccountExistingCheckUpCallback {

    private WeakReference<StartInputNumberView> startInputNumberView;
    private AuthVerificationTool authVerificationTool;
    private AccountExistingCheckUp accountExistingCheckUp;


    StartInputNumberPresenterImpl(StartInputNumberView startInputNumberView) {
        this.startInputNumberView = new WeakReference<>(startInputNumberView);
        this.authVerificationTool = new AuthVerificationToolImpl(this);
        this.accountExistingCheckUp = new AccountExistingCheckUpImpl(this);
    }

    @Override
    public void onClickNextButton(boolean isValidPhoneNumber) {
        if (isValidPhoneNumber) {
            startInputNumberView.get().validPhoneNumber();
        } else {
            startInputNumberView.get().invalidPhoneNumber();
        }
    }

    @Override
    public void verifyPhoneNumber(String fullPhoneNumber, Activity activity) {
        authVerificationTool.verifyPhoneNumber(fullPhoneNumber, activity);
    }

    /**
     * Verify phone number callbacks
     */

    @Override
    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential, String fullPhoneNumber) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential);
        accountExistingCheckUp.isAccountWithPhoneExist(fullPhoneNumber);
    }

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        startInputNumberView.get().onCodeSent(s, forceResendingToken);
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
        Log.d("ASMR", e.toString());
    }

    /**
     * Account exist check up callbacks
     */

    @Override
    public void accountIsNotExist() {
        startInputNumberView.get().accountNotExistButAuthIsSuccess();
    }

    @Override
    public void accountIsExist(QuerySnapshot querySnapshot) {
        startInputNumberView.get().accountExist();
    }
}
