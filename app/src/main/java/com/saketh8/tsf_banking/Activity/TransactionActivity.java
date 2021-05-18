package com.saketh8.tsf_banking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.saketh8.tsf_banking.Adapter.TransacAdapter;
import com.saketh8.tsf_banking.R;
import com.saketh8.tsf_banking.db.TransacDatabase;
import com.saketh8.tsf_banking.db.TransacEntity;

import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        rec = findViewById(R.id.transRec);
        try {
            List<TransacEntity> list = new getList().execute(TransactionActivity.this).get();
            TransacAdapter adapter = new TransacAdapter(list);
            rec.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(TransactionActivity.this);
            rec.setLayoutManager(layoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class getList extends AsyncTask<Context, Void, List<TransacEntity>> {

        @Override
        protected List<TransacEntity> doInBackground(Context... contexts) {
            TransacDatabase db = Room.databaseBuilder(contexts[0], TransacDatabase.class, "transaction").build();
            return db.getDao().getAll();
        }
    }
}