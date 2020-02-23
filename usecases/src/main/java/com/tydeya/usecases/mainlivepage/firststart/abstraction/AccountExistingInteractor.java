package com.tydeya.usecases.mainlivepage.firststart.abstraction;

public interface AccountExistingInteractor {

    void accountIsExist(AccountExistingInteractorCallbacks callbacks, String fullPhoneNumber);
}
