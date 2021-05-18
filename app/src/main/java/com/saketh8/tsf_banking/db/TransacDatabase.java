package com.saketh8.tsf_banking.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TransacEntity.class},version = 1,exportSchema = false)
abstract public class TransacDatabase extends RoomDatabase {
    public abstract TransacDao getDao();
}
