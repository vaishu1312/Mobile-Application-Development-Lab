package com.example.ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Retrieve2Activity extends AppCompatActivity {

    Button bt_dt;
    Spinner sp_dt;
    Cursor rs;
    SQLiteDatabase db;
    TextView resultr,tv1;
    String dept,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve2);

        bt_dt = findViewById(R.id.bt_dt);
        sp_dt = findViewById(R.id.sp_dt);
        resultr=findViewById(R.id.resultr);
        tv1=findViewById(R.id.tv1);

        bt_dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv1.setVisibility(View.GONE);
                db = openOrCreateDatabase("db", MODE_PRIVATE, null);
                dept = sp_dt.getSelectedItem().toString();

                rs = db.rawQuery("SELECT * FROM EMPLOYEES WHERE DEPT = ?;", new String[]{dept});
                msg = "";

                while(rs.moveToNext()) {
                    msg += rs.getString(0) + "  " + rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getString(3) + " " + rs.getString(4) + "\n";
                }
                if(msg.equals("")) {
                    resultr.setText("No employees in this department");
                }
                else{
                    tv1.setVisibility(View.VISIBLE);
                    resultr.setText(msg);
                }

                if(rs != null) { rs.close(); }
                if(db != null) { db.close(); }
            }
        });
    }
}
