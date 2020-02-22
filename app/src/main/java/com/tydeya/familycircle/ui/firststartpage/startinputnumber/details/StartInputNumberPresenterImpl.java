package com.tydeya.familycircle.ui.firststartpage.startinputnumber.details;

import com.tydeya.familycircle.framework.accountexistingcheckup.abstraction.AccountExistingCheckUpRecipient;
import com.tydeya.familycircle.framework.accountexistingcheckup.abstraction.AccountExistingCheckUpTool;
import com.tydeya.familycircle.framework.accountexistingcheckup.details.AccountExistingCheckUpToolImpl;
import com.tydeya.familycircle.framework.phoneverification.abstraction.PhoneVerificationHandler;
import com.tydeya.familycircle.framework.phoneverification.abstraction.PhoneVerificationResultRecipient;
import com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction.StartInputNumberPresenter;
import com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction.StartInputNumberView;

public class StartInputNumberPresenterImpl implements StartInputNumberPresenter, PhoneVerificationResultRecipient, AccountExistingCheckUpRecipient {

    private StartInputNumberView startInputNumberView;
    private PhoneVerificationHandler phoneVerificationHandler;
    private AccountExistingCheckUpTool accountExistingCheckUpTool;

    StartInputNumberPresenterImpl(StartInputNumberView startInputNumberView, PhoneVerificationHandler phoneVerificationHandler) {
        this.startInputNumberView = startInputNumberView;
        this.phoneVerificationHandler = phoneVerificationHandler;
    }

    @Override
    public void onAcceptButtonClick(boolean isPhoneNumberCorrect) {
        if (isPhoneNumberCorrect) {
            startInputNumberView.phoneNumberCountryCorrect();
        } else {
            startInputNumberView.phoneNumberCountryIncorrect();
        }
    }

    @Override
    public void sendVerificationCode(String fullPhoneNumber) {
        phoneVerificationHandler.verifyPhone(this, fullPhoneNumber);
    }

    @Override
    public void onCodeSent(String userCodeId) {
        startInputNumberView.verificationCodeSent(userCodeId);
    }

    @Override
    public void verificationSuccess(String fullPhoneNumber) {
        accountExistingCheckUpTool = new AccountExistingCheckUpToolImpl();
        accountExistingCheckUpTool.isExist(fullPhoneNumber, this);
    }

    @Override
    public void verificationFailure(Exception e) {
        startInputNumberView.verificationFailure(e);
    }

    @Override
    public void accountExistingCheckUpResult(boolean isAccountExist) {
        startInputNumberView.verificationSuccess(isAccountExist);
    }
}
