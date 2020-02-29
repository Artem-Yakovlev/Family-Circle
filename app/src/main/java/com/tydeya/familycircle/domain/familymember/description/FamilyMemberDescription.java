package com.tydeya.familycircle.domain.familymember.description;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class FamilyMemberDescription {

    private String name;

    @ColumnInfo(name = "birth_date")
    private long birthDate;

    @ColumnInfo(name = "image_address")
    private String imageAddress;

    public FamilyMemberDescription(String name, long birthDate, String imageAddress) {
        this.name = name;
        this.birthDate = birthDate;
        this.imageAddress = imageAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    @Override
    public String toString() {
        return "FamilyMemberDescription{" +
                "name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", imageAddress='" + imageAddress + '\'' +
                '}';
    }
}
