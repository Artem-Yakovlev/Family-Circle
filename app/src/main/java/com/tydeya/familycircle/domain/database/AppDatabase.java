package com.tydeya.familycircle.domain.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tydeya.familycircle.domain.familyinteractor.dao.FamilyMembersDao;
import com.tydeya.familycircle.data.familymember.FamilyMember;

@Database(entities = {FamilyMember.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FamilyMembersDao familyMembersDao();
}
