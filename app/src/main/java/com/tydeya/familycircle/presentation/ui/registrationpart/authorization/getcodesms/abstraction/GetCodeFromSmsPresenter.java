package com.tydeya.familycircle.presentation.ui.registrationpart.authorization.getcodesms.abstraction;

import android.app.Activity;

public interface GetCodeFromSmsPresenter {

    void onClickAcceptButton(String codeFromInput);

    void signInWithCode(String userCodeId, String codeFromSms, Activity activity);

    void onClickResendButton(Activity activity);
}
