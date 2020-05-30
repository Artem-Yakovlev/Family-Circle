package com.tydeya.familycircle.domain.verification.abstraction;

import android.app.Activity;

public interface AuthVerificationTool {

    void verifyPhoneNumber(String fullPhoneNumber, Activity activity);
}
