package com.tydeya.usecases.mainlivepage.firststart.abstraction;

public interface VerificationInteractor {

    void verifyPhoneNumber(VerificationInteractorCallbacks callbacks, String fullPhoneNumber);
}
