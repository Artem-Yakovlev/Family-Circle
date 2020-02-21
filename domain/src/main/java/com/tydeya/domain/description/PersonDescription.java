package com.tydeya.domain.description;

import java.util.Calendar;

public class PersonDescription extends Description {
    private Calendar birthDate;
    private ZodiacSign zodiacSign;

    public PersonDescription(String text, String photoAddress, Calendar birthDate) {
        super(text, photoAddress);
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
