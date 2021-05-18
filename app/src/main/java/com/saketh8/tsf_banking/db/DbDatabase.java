package com.saketh8.tsf_banking.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DbEntity.class},version = 1,exportSchema = false)
public abstract class DbDatabase extends RoomDatabase {
    public abstract DbDao getDao();
}
