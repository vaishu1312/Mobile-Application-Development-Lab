package com.example.ex5;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {
    Button bt_insert;
    EditText et_ec, et_name, et_salary;
    RadioGroup rg_gd;
    RadioButton check_rb_gd;
    Spinner sp_dt;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        bt_insert = findViewById(R.id.bt_insert);
        et_ec = findViewById(R.id.et_ec);
        et_name = findViewById(R.id.et_name);
        et_salary = findViewById(R.id.et_salary);
        rg_gd = findViewById(R.id.rg_gd);
        sp_dt = findViewById(R.id.sp_dt);

        bt_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = openOrCreateDatabase("db", MODE_PRIVATE, null);
                String msg = "";

                if(!et_name.getText().toString().matches("[A-Za-z.]+"))
                    msg += "Name error! ";

                if(rg_gd.getCheckedRadioButtonId() == -1)
                    msg += "Gender not selected! ";
                else
                    check_rb_gd = findViewById(rg_gd.getCheckedRadioButtonId());

                if(!et_ec.getText().toString().matches("[A-Za-z0-9]+"))
                    msg += "Invalid employee code! ";

                if(!et_salary.getText().toString().matches("[0-9]+"))
                    msg += "Salary error! ";

                try {
                    if (msg.equals("")) {
                        db.execSQL("INSERT INTO EMPLOYEES VALUES(?, ?, ?, ?, ?);", new String[]{et_name.getText().toString(), check_rb_gd.getText().toString(), et_ec.getText().toString(), sp_dt.getSelectedItem().toString(), et_salary.getText().toString()});
                        msg = "Inserted!";
                    }
                }catch(Exception ex){
                        //msg=ex.toString();
                        msg="Error inserting...Please enter correct details";
                }
                finally {
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                        if(db != null) { db.close(); }
                    }
            }
        });
    }
}
