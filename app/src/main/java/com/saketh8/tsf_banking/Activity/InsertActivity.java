package com.saketh8.tsf_banking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saketh8.tsf_banking.R;
import com.saketh8.tsf_banking.db.DbDatabase;
import com.saketh8.tsf_banking.db.DbEntity;

public class InsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        final EditText id = findViewById(R.id.id);
        final EditText name = findViewById(R.id.name);
        final EditText email = findViewById(R.id.email);
        final EditText balance = findViewById(R.id.balance);
        Button add = findViewById(R.id.addBtn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idE=Integer.parseInt(id.getText().toString());
                String nameE=name.getText().toString();
                String emailE=email.getText().toString();
                int balE=Integer.parseInt(balance.getText().toString());
                DbEntity temp = new DbEntity(idE, nameE,emailE , balE);
                try {
                    conDb cdb=new conDb(InsertActivity.this,temp);
                    boolean value=new addList().execute(cdb).get();
                    if(value)
                        Toast.makeText(getApplicationContext(),"Added",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  class conDb{
        public Context context;
        public DbEntity dbEntity;
        public conDb(Context c,DbEntity db){
            context=c;
            dbEntity=db;
        }
    }

    public class addList extends AsyncTask<conDb,Void,Boolean> {
        @Override
        protected Boolean doInBackground(conDb... conDbs) {
            DbDatabase db = Room.databaseBuilder(conDbs[0].context, DbDatabase.class, "customer").build();
            db.getDao().insert(conDbs[0].dbEntity);
            db.close();
            return true;
        }
    }
}