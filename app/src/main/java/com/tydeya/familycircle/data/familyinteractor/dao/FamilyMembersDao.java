package com.tydeya.familycircle.data.familyinteractor.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.tydeya.familycircle.domain.familymember.FamilyMember;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FamilyMembersDao {

    @Query("SELECT * FROM family_members")
    List<FamilyMember> getAll();

    @Query("SELECT * FROM family_members WHERE full_phone_number = :fullPhoneNumber")
    FamilyMember getByFullPhoneNumber(String fullPhoneNumber);

    @Insert
    void insert(FamilyMember familyMember);

    @Insert
    void insert(ArrayList<FamilyMember> familyMembers);

    @Update
    void update(FamilyMember familyMember);

    @Update
    void update(ArrayList<FamilyMember> familyMembers);

    @Delete
    void delete(FamilyMember employee);

}
