package com.tydeya.familycircle.data.familymember

import com.tydeya.familycircle.framework.datepickerdialog.DateRefactoring
import java.util.*

class FamilyMemberDto(familyMember: FamilyMember) {
    val name = familyMember.description.name
    val phone = familyMember.fullPhoneNumber
    val zodiacSign = parseDateForZodiacSignText(familyMember.description.birthDate)
    val birthDate = parseDateForPresenter(familyMember.description.birthDate)
    val workPlace = familyMember.careerData.workPlace
    val studyPlace = familyMember.careerData.studyPlace
    val imageAddress = familyMember.description.imageAddress

    private fun parseDateForPresenter(birthDateTimeStamp: Long): String {
        return if (birthDateTimeStamp == -1L) {
            ""
        } else {
            val calendar: Calendar = GregorianCalendar()
            calendar.timeInMillis = birthDateTimeStamp
            DateRefactoring.getDateLocaleText(calendar)
        }
    }

    private fun parseDateForZodiacSignText(birthDateTimeStamp: Long): String {
        return if (birthDateTimeStamp == -1L) {
            ""
        } else {
            val calendar: Calendar = GregorianCalendar()
            calendar.timeInMillis = birthDateTimeStamp
            ZodiacSign.getZodiacSign(calendar).name
        }
    }
}