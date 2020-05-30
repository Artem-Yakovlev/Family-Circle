package com.tydeya.familycircle.domain.verification.abstraction;

import android.app.Activity;

public interface AuthResendSmsTool {

    void resendSms(Activity activity, String fullPhoneNumber);
}
