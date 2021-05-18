package com.saketh8.tsf_banking.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer")
public class DbEntity {

    @PrimaryKey public int id;
    public String name;
    public String email;
    public int balance;

    public DbEntity(int id, String name, String email, int balance){
        this.id=id;
        this.name=name;
        this.email=email;
        this.balance=balance;
    }
}
