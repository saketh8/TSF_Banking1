package com.saketh8.tsf_banking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.saketh8.tsf_banking.R;
import com.saketh8.tsf_banking.db.DbDatabase;
import com.saketh8.tsf_banking.db.DbEntity;
import com.saketh8.tsf_banking.db.TransacDatabase;
import com.saketh8.tsf_banking.db.TransacEntity;

public class PaymentActivity extends AppCompatActivity {

    TextView id1, id2, name1, name2, bal1, bal2;
    EditText etAmt;
    Button complete, cancel;
    int sender = 0, receiver = 0;
    DbEntity entitySend, entityReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        if (getIntent() != null) {
            sender = getIntent().getIntExtra("sender", 101);
            receiver = getIntent().getIntExtra("receiver", 101);
        }
        getEntities();

        id1 = findViewById(R.id.id1);
        id2 = findViewById(R.id.id2);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        bal1 = findViewById(R.id.bal1);
        bal2 = findViewById(R.id.bal2);
        etAmt = findViewById(R.id.amtEt);
        complete = findViewById(R.id.complete);
        cancel = findViewById(R.id.cancel);

        id1.setText(id1.getText() + "" + entitySend.id);
        id2.setText(id2.getText() + "" + entityReceive.id);
        name1.setText(name1.getText() + "" + entitySend.name);
        name2.setText(name2.getText() + "" + entityReceive.name);
        bal1.setText(bal1.getText() + "" + entitySend.balance);
        bal2.setText(bal2.getText() + "" + entityReceive.balance);

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amtStr = etAmt.getText().toString();
                if (!amtStr.isEmpty()) {
                    int amt = Integer.parseInt(amtStr);
                    if (amt > entitySend.balance) {
                        Toast.makeText(PaymentActivity.this, "Amount greater than sender's account balance", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PaymentActivity.this, "Transferring...", Toast.LENGTH_SHORT).show();
                        conInt2 sendCon = new conInt2(PaymentActivity.this, sender, amt * -1);
                        conInt2 recCon = new conInt2(PaymentActivity.this, receiver, amt );
                        Long tsLong = System.currentTimeMillis()/1000;
                        TransacEntity te=new TransacEntity(tsLong,sender,receiver,amt,entitySend.name,entityReceive.name);
                        conInt3 traCon=new conInt3(PaymentActivity.this,te);
                        try {
                            new updateBal().execute(sendCon).get();
                            new updateBal().execute(recCon).get();
                            new updateTransac().execute(traCon).get();
                            Intent intent=new Intent(PaymentActivity.this,ListActivity.class);
                            intent.putExtra("sender",-1);
                            intent.putExtra("receiver",-1);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(PaymentActivity.this, "Enter amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(PaymentActivity.this)
                        .setTitle("Cancel Transaction?")
                        .setMessage("Are you sure you want to cancel this transaction?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
    }

    void getEntities() {
        conInt ci1 = new conInt(PaymentActivity.this, sender);
        conInt ci2 = new conInt(PaymentActivity.this, receiver);
        try {
            entitySend = new getEntity().execute(ci1).get();
            entityReceive = new getEntity().execute(ci2).get();
            System.out.println(entitySend.id);
            System.out.println(entityReceive.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class conInt {
        public int id;
        public Context context;

        public conInt(Context ctx, int i) {
            id = i;
            context = ctx;
        }
    }

    public class conInt2 {
        public int id, amt;
        public Context context;

        public conInt2(Context ctx, int i, int a) {
            id = i;
            context = ctx;
            amt = a;
        }
    }

    public class conInt3{
        TransacEntity entity;
        Context context;

        public  conInt3(Context ctx,TransacEntity entity1){
            entity=entity1;
            context=ctx;
        }
    }

    public class getEntity extends AsyncTask<PaymentActivity.conInt, Void, DbEntity> {
        @Override
        protected DbEntity doInBackground(PaymentActivity.conInt... conInts) {
            DbDatabase db = Room.databaseBuilder(conInts[0].context, DbDatabase.class, "customer").build();
            return db.getDao().getById(conInts[0].id);
        }
    }

    public class updateBal extends AsyncTask<conInt2, Void, Void> {

        @Override
        protected Void doInBackground(conInt2... conInts) {
            DbDatabase db = Room.databaseBuilder(conInts[0].context, DbDatabase.class, "customer").build();
            db.getDao().updateBalance(conInts[0].amt, conInts[0].id);
            return null;
        }
    }

    public class updateTransac extends AsyncTask<conInt3,Void,Void>{

        @Override
        protected Void doInBackground(conInt3... conInt3s) {
            TransacDatabase db=Room.databaseBuilder(conInt3s[0].context,TransacDatabase.class,"transaction").build();
            db.getDao().insert(conInt3s[0].entity);
            return null;
        }
    }
}