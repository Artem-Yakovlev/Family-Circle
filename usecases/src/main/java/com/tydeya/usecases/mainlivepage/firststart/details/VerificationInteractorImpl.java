package com.tydeya.usecases.mainlivepage.firststart.details;

import com.tydeya.data.accountsync.abstraction.PhoneVerification;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.VerificationInteractor;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.VerificationInteractorCallbacks;

public class VerificationInteractorImpl implements VerificationInteractor {

    private VerificationInteractorCallbacks callbacks;
    private PhoneVerification phoneVerification;

    public VerificationInteractorImpl(VerificationInteractorCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void verifyPhoneNumber(VerificationInteractorCallbacks callbacks, String fullPhoneNumber) {

    }
}
