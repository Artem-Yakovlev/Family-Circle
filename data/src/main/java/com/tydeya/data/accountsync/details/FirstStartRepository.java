package com.tydeya.data.accountsync.details;

import com.tydeya.data.accountsync.abstraction.AccountExistingCheckUpRecipient;
import com.tydeya.data.accountsync.abstraction.AccountExistingCheckUpTool;
import com.tydeya.data.accountsync.abstraction.AccountSync;
import com.tydeya.data.accountsync.abstraction.PhoneVerification;
import com.tydeya.data.accountsync.abstraction.PhoneVerificationHandler;
import com.tydeya.data.accountsync.abstraction.PhoneVerificationResultRecipient;

public class FirstStartRepository implements AccountSync, PhoneVerification, AccountExistingCheckUpRecipient,
        PhoneVerificationResultRecipient {

    private AccountExistingCheckUpTool accountExistingCheckUpTool;
    private PhoneVerificationHandler phoneVerificationHandler;

    FirstStartRepository(AccountExistingCheckUpTool accountExistingCheckUpTool,
                         PhoneVerificationHandler phoneVerificationHandler) {
        this.accountExistingCheckUpTool = accountExistingCheckUpTool;
        this.phoneVerificationHandler = phoneVerificationHandler;
    }

    /**
     * Account existing checker
     */

    @Override
    public void isAccountExist(String fullPhoneNumber) {
        accountExistingCheckUpTool.isExist(fullPhoneNumber, this);
    }

    @Override
    public void accountCreate() {

    }


    @Override
    public void accountExistingCheckUpResult(boolean isAccountExist) {

    }

    /**
     * Phone verification
     */

    @Override
    public void phoneVerify(String fullPhoneNumber) {
        phoneVerificationHandler.verifyPhone(this, fullPhoneNumber);
    }

    @Override
    public void onCodeSent(String userCodeId) {

    }

    @Override
    public void verificationSuccess(String fullPhoneNumber) {

    }

    @Override
    public void verificationFailure(Exception e) {

    }
}
