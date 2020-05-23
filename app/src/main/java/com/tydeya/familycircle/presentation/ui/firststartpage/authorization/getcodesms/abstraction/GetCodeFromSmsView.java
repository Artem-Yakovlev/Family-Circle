package com.tydeya.familycircle.presentation.ui.firststartpage.authorization.getcodesms.abstraction;

public interface GetCodeFromSmsView {

    void invalidCodeFormatAlert();

    void codeFormatIsValid();

    void codeIsInvalid();

    void accountIsExist();

    void accountIsNotExistButVerificationIsSuccess(String fullPhoneNumber);

    void timerTick(String timeDown);

    void timerFinish();
}
