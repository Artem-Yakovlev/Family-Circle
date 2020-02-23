package com.tydeya.familycircle.ui.firststartpage.startinputnumber.details;

import com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction.StartInputNumberPresenter;
import com.tydeya.familycircle.ui.firststartpage.startinputnumber.abstraction.StartInputNumberView;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.AccountExistingInteractor;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.AccountExistingInteractorCallbacks;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.VerificationInteractor;
import com.tydeya.usecases.mainlivepage.firststart.abstraction.VerificationInteractorCallbacks;
import com.tydeya.usecases.mainlivepage.firststart.details.AccountExistingInteractorImpl;
import com.tydeya.usecases.mainlivepage.firststart.details.VerificationInteractorImpl;

public class StartInputNumberPresenterImpl implements StartInputNumberPresenter,
        VerificationInteractorCallbacks, AccountExistingInteractorCallbacks {

    private StartInputNumberView startInputNumberView;
    //TODO Inject this objects
    private AccountExistingInteractor accountExistingInteractor;
    private VerificationInteractor verificationInteractor;

    StartInputNumberPresenterImpl(StartInputNumberView startInputNumberView) {
        this.startInputNumberView = startInputNumberView;
        accountExistingInteractor = new AccountExistingInteractorImpl(this);
        verificationInteractor = new VerificationInteractorImpl(this);
    }

    /**
     * View callbacks
     */

    @Override
    public void onNextButtonClick(boolean isPhoneNumberCorrect) {
        if (isPhoneNumberCorrect) {
            startInputNumberView.phoneNumberCountryCorrect();
        } else {
            startInputNumberView.phoneNumberCountryIncorrect();
        }
    }

    @Override
    public void verifyDialogPositiveButton(String fullPhoneNumber) {
        verificationInteractor.verifyPhoneNumber(this, fullPhoneNumber);
    }

    /**
     * Authentication callbacks
     */

    @Override
    public void onCodeSent(String userCodeId) {
        startInputNumberView.verificationCodeSent(userCodeId);
    }

    @Override
    public void verificationSuccess(String fullPhoneNumber) {
        accountExistingInteractor.accountIsExist(this, fullPhoneNumber);
    }

    @Override
    public void verificationFailure(Exception e) {
        startInputNumberView.verificationFailure(e);
    }

    /**
     * Account exiting tool callbacks
     */

    @Override
    public void accountExistingCheckUpResult(boolean isAccountExist) {
        startInputNumberView.verificationSuccess(isAccountExist);
    }
}
