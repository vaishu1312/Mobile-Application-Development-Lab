package com.example.ex2;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class page2 extends AppCompatActivity {

    TextView name,dob,gender,marital,contact,addict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        name = findViewById(R.id.name_tb);
        dob = findViewById(R.id.dob_tb);
        gender = findViewById(R.id.gender_tb);
        marital = findViewById(R.id.marital_tb);
        contact = findViewById(R.id.contact_tb);
        addict = findViewById(R.id.addict_tb);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            //set all string to null
        } else {
            name.setText( extras.getString("name"));
            gender.setText( extras.getString("gender"));
            marital.setText( extras.getString("marital"));
            contact.setText( extras.getString("contact"));
            addict.setText( extras.getString("addiction"));
            String date=extras.getString("day")+"-"+extras.getString("month")+"-"+extras.getString("year");
            dob.setText(date);

        }

    }

}
