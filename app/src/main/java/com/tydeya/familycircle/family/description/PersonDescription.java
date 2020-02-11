package com.tydeya.familycircle.family.description;

import android.net.Uri;

import java.util.Calendar;

public class PersonDescription extends Description {
    private Calendar birthDate;
    private ZodiacSign zodiacSign;

    public PersonDescription(String text, Uri photo, Calendar birthDate) {
        super(text, photo);
        this.birthDate = birthDate;
        this.zodiacSign = ZodiacSign.getZodiacSign(birthDate);
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    public ZodiacSign getZodiacSign() {
        return zodiacSign;
    }


}
