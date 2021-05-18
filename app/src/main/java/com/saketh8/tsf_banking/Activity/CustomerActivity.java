package com.saketh8.tsf_banking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.saketh8.tsf_banking.R;
import com.saketh8.tsf_banking.db.DbDatabase;
import com.saketh8.tsf_banking.db.DbEntity;

public class CustomerActivity extends AppCompatActivity {

    TextView name, email, balance;
    int id;
    int sender = 0, receiver = 0;
    Button send, receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        if (getIntent() != null) {
            id = getIntent().getIntExtra("id", 101);
            sender = getIntent().getIntExtra("sender", -1);
            receiver = getIntent().getIntExtra("receiver", -1);
        }

        name = findViewById(R.id.nameTxt);
        email = findViewById(R.id.emailTxt);
        balance = findViewById(R.id.balanceTxt);
        send = findViewById(R.id.senderBtn);
        receive = findViewById(R.id.receiverBtn);

        if (sender == -1) {
            receive.setVisibility(View.GONE);
        } else {
            send.setVisibility(View.GONE);
        }

        conInt ci = new conInt(CustomerActivity.this, id);
        try {
            DbEntity dbEntity = new getEntity().execute(ci).get();
            name.setText(dbEntity.name);
            email.setText(dbEntity.email);
            balance.setText("Balance: " + dbEntity.balance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sender = id;
                Intent intent = new Intent(CustomerActivity.this, ListActivity.class);
                intent.putExtra("sender", sender);
                intent.putExtra("receiver", receiver);
                startActivity(intent);
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receiver=id;
                Intent intent=new Intent(CustomerActivity.this,PaymentActivity.class);
                intent.putExtra("sender",sender);
                intent.putExtra("receiver",receiver);
                startActivity(intent);
            }
        });
    }

    public class conInt {
        public int id;
        public Context context;

        public conInt(Context ctx, int i) {
            id = i;
            context = ctx;
        }
    }

    public class getEntity extends AsyncTask<conInt, Void, DbEntity> {
        @Override
        protected DbEntity doInBackground(conInt... conInts) {
            DbDatabase db = Room.databaseBuilder(conInts[0].context, DbDatabase.class, "customer").build();
            return db.getDao().getById(conInts[0].id);
        }
    }
}