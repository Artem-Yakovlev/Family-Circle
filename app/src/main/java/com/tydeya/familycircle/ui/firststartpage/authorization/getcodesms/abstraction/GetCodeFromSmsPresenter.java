package com.tydeya.familycircle.ui.firststartpage.authorization.getcodesms.abstraction;

import android.app.Activity;

public interface GetCodeFromSmsPresenter {

    void onClickAcceptButton(String codeFromInput);

    void signInWithCode(String userCodeId, String codeFromSms, Activity activity);
}
