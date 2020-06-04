package com.tydeya.familycircle.presentation.ui.registrationpart.authorization.getcodesms.details;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tydeya.familycircle.domain.account.AccountSyncTool;
import com.tydeya.familycircle.domain.account.AccountExistingCheckUpCallback;
import com.tydeya.familycircle.framework.signInWithCode.abstraction.SignInWithPhoneCodeTool;
import com.tydeya.familycircle.framework.signInWithCode.abstraction.SignInWithPhoneCodeToolCallback;
import com.tydeya.familycircle.framework.signInWithCode.details.SignInWithPhoneCodeToolImpl;
import com.tydeya.familycircle.domain.verification.abstraction.AuthResendSmsTool;
import com.tydeya.familycircle.domain.verification.details.AuthResendSmsToolImpl;
import com.tydeya.familycircle.presentation.ui.registrationpart.authorization.getcodesms.abstraction.GetCodeFromSmsPresenter;
import com.tydeya.familycircle.presentation.ui.registrationpart.authorization.getcodesms.abstraction.GetCodeFromSmsView;
import com.tydeya.familycircle.presentation.ui.registrationpart.authorization.getcodesms.abstraction.ResendCountDownTimerCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GetCodeFromSmsPresenterImpl implements GetCodeFromSmsPresenter, SignInWithPhoneCodeToolCallback,
        AccountExistingCheckUpCallback, ResendCountDownTimerCallback {

    private GetCodeFromSmsView view;
    private SignInWithPhoneCodeTool signInWithPhoneCodeTool;
    private AccountSyncTool accountSyncTool;
    private String phoneNumber;

    private AuthResendSmsTool authResendSmsTool;
    private ResendCountDownTimer resendTimer;


    GetCodeFromSmsPresenterImpl(GetCodeFromSmsView getCodeFromSmsView, FirebaseAuth firebaseAuth, String phoneNumber) {
        this.view = getCodeFromSmsView;
        this.signInWithPhoneCodeTool = new SignInWithPhoneCodeToolImpl(this, firebaseAuth);
        this.accountSyncTool = new AccountSyncTool(this);
        this.authResendSmsTool = new AuthResendSmsToolImpl();
        this.phoneNumber = phoneNumber;
        this.resendTimer = new ResendCountDownTimer(this);
    }

    /**
     * Accept button processing
     **/

    @Override
    public void onClickAcceptButton(String codeFromInput) {
        if (codeFormatCheck(codeFromInput)) {
            view.codeFormatIsValid();
        }
    }

    @Override
    public void signInWithCode(String userCodeId, String codeFromSms, Activity activity) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(userCodeId, codeFromSms);
        signInWithPhoneCodeTool.signInWithCredentialCode(credential, activity);
    }

    private boolean codeFormatCheck(String codeFromInput) {
        if (codeFromInput.length() < 6) {
            view.invalidCodeFormatAlert();
            return false;
        }
        return true;
    }

    /**
     * Resend button processing
     */

    @Override
    public void onClickResendButton(Activity activity) {
        resendTimer.start();
        resendSms(activity);
    }

    private void resendSms(Activity activity) {
        authResendSmsTool.resendSms(activity, phoneNumber);
    }

    /**
     * Sign in with credential code callbacks
     */

    @Override
    public void signInIsSuccessful() {
        accountSyncTool.isAccountWithPhoneExist(phoneNumber);
    }

    @Override
    public void signInCodeIsInvalid() {
        view.codeIsInvalid();
    }

    /**
     * Account existing checker callbacks
     */

    @Override
    public void accountIsExist(@NotNull String userId,
                               @NotNull List<String> families) {
        view.accountIsExist();
    }

    @Override
    public void accountIsNotExist() {
        view.accountIsNotExistButVerificationIsSuccess(phoneNumber);
    }

    /**
     * Resend count down timer callbacks
     */

    @Override
    public void timerTickGetText(String timeDown) {
        view.timerTick(timeDown);
    }

    @Override
    public void timerFinish() {
        view.timerFinish();
    }
}
