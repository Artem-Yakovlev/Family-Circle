package com.tydeya.familycircle.presentation.ui.registrationpart.authorization.inputnumber.details;

import android.app.Activity;

import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.domain.account.AccountExistingCheckUpCallback;
import com.tydeya.familycircle.domain.account.AccountSyncTool;
import com.tydeya.familycircle.domain.verification.abstraction.AuthVerificationCallback;
import com.tydeya.familycircle.domain.verification.abstraction.AuthVerificationTool;
import com.tydeya.familycircle.domain.verification.details.AuthVerificationToolImpl;
import com.tydeya.familycircle.presentation.ui.registrationpart.authorization.inputnumber.abstraction.StartInputNumberPresenter;
import com.tydeya.familycircle.presentation.ui.registrationpart.authorization.inputnumber.abstraction.StartInputNumberView;

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
    public void onVerificationCompleted(String fullPhoneNumber) {
        accountSyncTool.isAccountWithPhoneExist(fullPhoneNumber);
    }

    @Override
    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
        startInputNumberView.get().onCodeSent(s, forceResendingToken);
    }

    @Override
    public void onVerificationFailed(Exception e) {
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
                               @NotNull List<String> families) {
        startInputNumberView.get().accountExist();
    }

}
