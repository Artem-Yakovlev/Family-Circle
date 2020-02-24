package com.tydeya.familycircle.ui.firststartpage.authorization.getcodesms.abstraction;

public interface GetCodeFromSmsView {

    void invalidCodeFormatAlert();

    void codeFormatIsValid();

    void codeIsInvalid();

    void accountIsExist();

    void accountIsNotExistButVerificationIsSuccess(String fullPhoneNumber);
}
