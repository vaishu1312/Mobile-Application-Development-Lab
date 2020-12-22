package com.example.ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RetrieveActivity extends AppCompatActivity {
    Button bt_ec;
    EditText et_ec;
    TextView name,gender,ecode,dept,sal;
    SQLiteDatabase db;
    Cursor rs;
    String ec;
    LinearLayout ll2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        name = findViewById(R.id.name_tb);
        gender = findViewById(R.id.gender_tb);
        ecode = findViewById(R.id.ec_tb);
        dept = findViewById(R.id.dept_tb);
        sal = findViewById(R.id.sal_tb);
        ll2=findViewById(R.id.ll2);

        bt_ec = findViewById(R.id.bt_ec);
        et_ec = findViewById(R.id.et_ec);

        bt_ec.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                /*name.setText("");
                gender.setText("");
                ecode.setText("");
                dept.setText("");
                sal.setText("");*/
                ll2.setVisibility(View.INVISIBLE);

                db = openOrCreateDatabase("db", MODE_PRIVATE, null);

                ec = et_ec.getText().toString();
                rs = db.rawQuery("SELECT * FROM EMPLOYEES WHERE E_CODE = ?;", new String[]{ec});

                try {
                    rs.moveToFirst();
                    name.setText(rs.getString(0));
                    gender.setText(rs.getString(1));
                    ecode.setText(rs.getString(2));
                    dept.setText(rs.getString(3));
                    sal.setText(rs.getString(4));
                    ll2.setVisibility(View.VISIBLE);
                }catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(), "No such Employee!", Toast.LENGTH_LONG).show();
                }
                finally {
                    if(rs != null) { rs.close(); }
                    if(db != null) { db.close(); }
                }

            }
        });
    }
}
