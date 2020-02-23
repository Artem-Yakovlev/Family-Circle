package com.tydeya.usecases.mainlivepage.firststart.details;

import com.tydeya.data.accountsync.abstraction.AccountSync;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.AccountExistingInteractor;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.AccountExistingInteractorCallbacks;

public class AccountExistingInteractorImpl implements AccountExistingInteractor {

    private AccountExistingInteractorCallbacks callbacks;
    private AccountSync accountSync;

    public AccountExistingInteractorImpl(AccountExistingInteractorCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void accountIsExist(AccountExistingInteractorCallbacks callbacks, String fullPhoneNumber) {
    }
}
