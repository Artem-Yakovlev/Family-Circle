package com.tydeya.familycircle.data.familymember;

import com.tydeya.familycircle.data.familymember.FamilyMember;
import com.tydeya.familycircle.data.familymember.ZodiacSign;
import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FamilyMemberDto {

    private String name;
    private String phone;
    private String zodiacSign;
    private String birthDate;
    private String workPlace;
    private String studyPlace;
    private String imageAddress;

    public FamilyMemberDto(FamilyMember familyMember) {
        this.name = familyMember.getDescription().getName();
        this.birthDate = parseDateForPresenter(familyMember.getDescription().getBirthDate());
        this.zodiacSign = parseDateForZodiacSignText(familyMember.getDescription().getBirthDate());
        this.studyPlace = familyMember.getCareerData().getStudyPlace();
        this.workPlace = familyMember.getCareerData().getWorkPlace();
        this.phone = familyMember.getFullPhoneNumber();
        this.imageAddress = familyMember.getDescription().getImageAddress();
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

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getStudyPlace() {
        return studyPlace;
    }

    public void setStudyPlace(String studyPlace) {
        this.studyPlace = studyPlace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }
}
