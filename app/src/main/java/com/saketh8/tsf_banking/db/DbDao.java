package com.saketh8.tsf_banking.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DbDao {

    @Insert
    void insert(DbEntity dbEntity);

    @Query("SELECT * FROM customer")
    List<DbEntity> getAll();

    @Query("SELECT * FROM customer where id=:Id")
    DbEntity getById(int Id);

    @Query("UPDATE customer SET balance=balance+:amt WHERE id=:Id")
    void updateBalance(int amt,int Id);
}
