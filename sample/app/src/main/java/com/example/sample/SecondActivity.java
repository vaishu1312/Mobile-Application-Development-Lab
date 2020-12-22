package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;

public class SecondActivity extends AppCompatActivity implements Serializable {
    String name, address;
    TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);

        Intent intent = getIntent();
        SecondActivity details = (SecondActivity) intent.getSerializableExtra("details");

        tv1.setText(details.name);
        tv2.setText(details.address);
    }
}
