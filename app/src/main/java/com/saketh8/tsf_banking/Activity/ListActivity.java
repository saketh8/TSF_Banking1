package com.saketh8.tsf_banking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.saketh8.tsf_banking.Adapter.MyAdapter;
import com.saketh8.tsf_banking.db.DbDatabase;
import com.saketh8.tsf_banking.db.DbEntity;
import com.saketh8.tsf_banking.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    RecyclerView recycler;
    MyAdapter adap;
    int send=0,receive=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        TextView heading=findViewById(R.id.topText);

        if(getIntent()!=null){
            send=getIntent().getIntExtra("sender",-1);
            receive=getIntent().getIntExtra("receiver",-1);
        }

        if(send==-1){
            heading.setText("Select sender");
        }
        else{
            heading.setText("Select receiver");
        }

        recycler = findViewById(R.id.recycler);

        List<DbEntity> list=new ArrayList<>();
        try {
            list=new getList().execute(ListActivity.this).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(send!=-1) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).id == send)
                    list.remove(i);
            }
        }

        adap = new MyAdapter(list,ListActivity.this,send,receive);

        recycler.setAdapter(adap);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recycler.setLayoutManager(layout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(ListActivity.this,MainActivity.class);
        intent.putExtra("first",false);
        startActivity(intent);
    }

    class getList extends AsyncTask<Context,Void, List<DbEntity>>{
        @Override
        protected List<DbEntity> doInBackground(Context... contexts) {
            DbDatabase db = Room.databaseBuilder(contexts[0], DbDatabase.class, "customer").build();
            List<DbEntity> list=db.getDao().getAll();
            db.close();
            System.out.println("Length is "+ list.size());
            return list;
        }
    }

}