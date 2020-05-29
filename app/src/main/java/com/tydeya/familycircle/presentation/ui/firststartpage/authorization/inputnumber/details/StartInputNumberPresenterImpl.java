package com.tydeya.familycircle.presentation.ui.firststartpage.authorization.inputnumber.details;

import android.app.Activity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.data.authentication.accountsync.AccountExistingCheckUpCallback;
import com.tydeya.familycircle.data.authentication.accountsync.AccountSyncTool;
import com.tydeya.familycircle.framework.verification.abstraction.AuthVerificationCallback;
import com.tydeya.familycircle.framework.verification.abstraction.AuthVerificationTool;
import com.tydeya.familycircle.framework.verification.details.AuthVerificationToolImpl;
import com.tydeya.familycircle.presentation.ui.firststartpage.authorization.inputnumber.abstraction.StartInputNumberPresenter;
import com.tydeya.familycircle.presentation.ui.firststartpage.authorization.inputnumber.abstraction.StartInputNumberView;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

public class StartInputNumberPresenterImpl implements StartInputNumberPresenter,
        AuthVerificationCallback, AccountExistingCheckUpCallback {

    private WeakReference<StartInputNumberView> startInputNumberView;
    private AuthVerificationTool authVerificationTool;
    private AccountSyncTool accountSyncTool;


    StartInputNumberPresenterImpl(StartInputNumberView startInputNumberView) {
        this.startInputNumberView = new WeakReference<>(startInputNumberView);
        this.authVerificationTool = new AuthVerificationToolImpl(this);
        this.accountSyncTool = new AccountSyncTool(this);
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
        accountSyncTool.isAccountWithPhoneExist(fullPhoneNumber);
    }

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        startInputNumberView.get().onCodeSent(s, forceResendingToken);
    }

    @Override
    public void onVerificationFailed(FirebaseException e) {
        startInputNumberView.get().verificationFailed(e);
    }

    /**
     * Account exist check up callbacks
     */

    @Override
    public void accountIsNotExist() {
        startInputNumberView.get().accountNotExistButAuthIsSuccess();
    }

    @Override
    public void accountIsExist(@NotNull String userId,
                               @NotNull List<String> families,
                               @NotNull String currentFamily) {
        startInputNumberView.get().accountExist();
    }

}
