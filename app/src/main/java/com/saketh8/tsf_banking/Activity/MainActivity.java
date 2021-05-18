package com.saketh8.tsf_banking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.saketh8.tsf_banking.Activity.ListActivity;
import com.saketh8.tsf_banking.R;

public class MainActivity extends AppCompatActivity {

    Button viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewBtn=findViewById(R.id.viewBtn);

        boolean f=true;
        if(getIntent()!=null){
            f=getIntent().getBooleanExtra("first",true);
        }
        if(f){
            Animation anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
            Animation anim1= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            viewBtn.startAnimation(anim);
            findViewById(R.id.logo).startAnimation(anim);
            findViewById(R.id.designTxt).startAnimation(anim1);
            findViewById(R.id.line).startAnimation(anim1);
            findViewById(R.id.titleTxt).startAnimation(anim1);
            findViewById(R.id.transactions).startAnimation(anim1);
        }

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("sender",-1);
                intent.putExtra("receiver",-1);
                startActivity(intent);
            }
        });

        findViewById(R.id.transactions).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TransactionActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}