package com.tydeya.familycircle.domain.familymember.dto;

import com.tydeya.familycircle.domain.familymember.FamilyMember;
import com.tydeya.familycircle.domain.familymember.ZodiacSign;

public class FamilyMemberDto {

    private String name;
    private ZodiacSign zodiacSign;
    private String birthDate;

    FamilyMemberDto(FamilyMember familyMember) {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZodiacSign getZodiacSign() {
        return zodiacSign;
    }

    public void setZodiacSign(ZodiacSign zodiacSign) {
        this.zodiacSign = zodiacSign;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
