package com.saketh8.tsf_banking.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction")
public class TransacEntity {

    @PrimaryKey public Long key;
    @ColumnInfo public int sendId;
    @ColumnInfo public int recId;
    @ColumnInfo public int amt;
    @ColumnInfo public String sendName;
    @ColumnInfo public String recName;

    public TransacEntity(Long key,int sendId,int recId,int amt,String sendName,String recName){
        this.key=key;
        this.sendId=sendId;
        this.recId=recId;
        this.amt=amt;
        this.sendName=sendName;
        this.recName=recName;
    }
}
