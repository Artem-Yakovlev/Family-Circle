package com.tydeya.familycircle.domain.familymember.otherdata;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class FamilyMemberCareerData {

    @ColumnInfo(name = "study_place")
    private String studyPlace;

    @ColumnInfo(name = "work_place")
    private String workPlace;

    public FamilyMemberCareerData(String studyPlace, String workPlace) {
        this.studyPlace = studyPlace;
        this.workPlace = workPlace;
    }

    public String getStudyPlace() {
        return studyPlace;
    }

    public void setStudyPlace(String studyPlace) {
        this.studyPlace = studyPlace;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }
}
