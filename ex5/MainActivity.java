package com.example.ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bt_cre,bt_ins,bt_upd,bt_del,bt_ret,bt_ret_dt;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_cre = findViewById(R.id.bt_cre);
        bt_ins = findViewById(R.id.bt_ins);
        bt_upd = findViewById(R.id.bt_upd);
        bt_del = findViewById(R.id.bt_del);
        bt_ret = findViewById(R.id.bt_ret);
        bt_ret_dt=findViewById(R.id.bt_ret_dt);
        
        bt_cre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openOrCreateDatabase("db", MODE_PRIVATE, null);
                //db.execSQL("DROP TABLE IF EXISTS EMPLOYEES;");
                db.execSQL("CREATE TABLE IF NOT EXISTS EMPLOYEES(NAME VARCHAR(20) NOT NULL, GENDER VARCHAR(20) NOT NULL, E_CODE VARCHAR(20) PRIMARY KEY NOT NULL, DEPT VARCHAR(20) NOT NULL, SALARY VARCHAR(20) NOT NULL);");
                Toast.makeText(getApplicationContext(), "Created table!", Toast.LENGTH_SHORT).show();
                if(db != null) { db.close(); }
            }
        });
        bt_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), InsertActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        bt_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), UpdateActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        bt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), DeleteActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        bt_ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), RetrieveActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        bt_ret_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), Retrieve2Activity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }
}
