package com.tydeya.familycircle.ui.firststartpage.authorization.getcodesms.details;

import android.os.CountDownTimer;

import com.tydeya.familycircle.ui.firststartpage.authorization.getcodesms.abstraction.ResendCountDownTimerCallback;

public class ResendCountDownTimer extends CountDownTimer {

    private ResendCountDownTimerCallback callback;

    ResendCountDownTimer(ResendCountDownTimerCallback callback) {
        super(60000, 1000);
        this.callback = callback;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((millisUntilFinished / 60000) / 1000).append(":");
        if ((millisUntilFinished % 60000) / 1000 < 10) {
            stringBuilder.append("0");
        }
        stringBuilder.append((millisUntilFinished % 60000) / 1000);
        callback.timerTickGetText(stringBuilder.toString());
    }

    @Override
    public void onFinish() {
        callback.timerFinish();
    }
}
