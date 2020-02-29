package com.tydeya.familycircle.domain.familymember.dto;

import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familymember.ZodiacSign;
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FamilyMemberDto {

    private String name;
    private String zodiacSign;
    private String birthDate;

    public FamilyMemberDto(FamilyMember familyMember) {
        this.name = familyMember.getDescription().getName();
        this.birthDate = parseDateForPresenter(familyMember.getDescription().getBirthDate());
        this.zodiacSign = parseDateForZodiacSignText(familyMember.getDescription().getBirthDate());
    }

    /**
     * Parse data for Dto
     */

    private String parseDateForPresenter(long birthDateTimeStamp) {
        if (birthDateTimeStamp == -1) {
            return "";
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(birthDateTimeStamp);
            return DateRefactoring.getDateLocaleText(calendar);
        }
    }

    private String parseDateForZodiacSignText(long birthDateTimeStamp) {
        if (birthDateTimeStamp == -1) {
            return "";
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(birthDateTimeStamp);
            return ZodiacSign.getZodiacSign(calendar).name();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZodiacSign() {
        return zodiacSign;
    }

    public void setZodiacSign(String zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
