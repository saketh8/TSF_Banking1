package com.saketh8.tsf_banking.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransacDao {

    @Insert
    void insert(TransacEntity transacEntity);

    @Query("SELECT * FROM `transaction`")
    List<TransacEntity> getAll();
}
