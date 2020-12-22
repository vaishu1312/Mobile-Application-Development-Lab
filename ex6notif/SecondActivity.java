package com.example.ex6notif;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView tv;

    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv=findViewById(R.id.tv);

        Intent myIntent = getIntent();
        String str=myIntent.getStringExtra("msg");
        tv.setText("Your email-id is:"+str);
    }
}
