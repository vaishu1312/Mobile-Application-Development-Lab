package com.example.ex2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button submit,reset;
    EditText name,contact_et;
    DatePicker dob;
    RadioGroup gd;
    Spinner ms_sp;
    CheckBox smoke_cb,alcohol_cb;
    RadioButton check_rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = findViewById(R.id.submit_bt);
        reset = findViewById(R.id.reset_bt);
        name = findViewById(R.id.name_et);
        dob = findViewById(R.id.date_dp);
        gd =  findViewById(R.id.gd_rg);
        ms_sp = (Spinner) findViewById(R.id.ms_sp);
        contact_et = findViewById(R.id.contact_et);
        smoke_cb = findViewById(R.id.smoke_cb);
        alcohol_cb = findViewById(R.id.alcohol_cb);

        ArrayAdapter<CharSequence>  adapter=ArrayAdapter.createFromResource(this, R.array.marital,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ms_sp.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), page2.class);

                String add="";

                String name_field =  name.getText().toString();

                String day=String.valueOf(dob.getDayOfMonth());
                String month=String.valueOf(dob.getMonth()+1);
                String year=String.valueOf(dob.getYear());

                int selectedId = gd.getCheckedRadioButtonId();
                //Log.d("radio","rad bt id: "+selectedId);//i is 1,2,3 as it is not assigned
                check_rb = (RadioButton) findViewById(selectedId);
                String gender = check_rb.getText().toString(); //getTExt returns CharSequence

                String marital_stat=ms_sp.getSelectedItem().toString();
                //Log.d("sat","sat string : "+marital_stat);
                //long spin_id =ms_sp.getSelectedItemId();  //id sarts with 0
                //Log.d("sat","sat item id: "+spin_id);
                //System.out.println("id:"+spin_id);

                String contact = contact_et.getText().toString();

                if (smoke_cb.isChecked())
                    add+="Smoking ";
                if(alcohol_cb.isChecked())
                    add+="Alcohol";

                Bundle b = new Bundle();

                b.putString("name", name_field);
                b.putString("day", day);
                b.putString("month", month);
                b.putString("year", year);
                b.putString("gender", gender);
                b.putString("marital", marital_stat);
                b.putString("contact", contact);
                b.putString("addiction", add);
                myIntent.putExtras(b);
                startActivity(myIntent);
            }
        });
    }

    public void resetFields(View v){
        name.setText("");
        gd.clearCheck();
        ms_sp.setSelection(0);
        contact_et.setText("");
        smoke_cb.setChecked(false);
        alcohol_cb.setChecked(false);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dob.init(year, month, day, null);
    }

    /* public void onClick(View view)
    {
        switch(view.getId()) {
            case R.id.submit_bt:
                Intent myIntent = new Intent(getBaseContext(),page2.class);
                startActivity(myIntent);
        }
    }*/
}