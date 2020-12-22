package com.example.ex10_smsalert;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ComposeActivity extends AppCompatActivity {

    public static boolean active = false;
    Button bt_send;
    EditText et_ph, et_msg;

    protected void onStart() {
        super.onStart();
        active = true;  //active in background
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        active = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        bt_send = findViewById(R.id.bt_send);
        et_ph = findViewById(R.id.et_ph);
        et_msg = findViewById(R.id.et_msg);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager smsManager = SmsManager.getDefault();
                //smsManager.sendTextMessage(destinationAddress, scAddress, smsMessage,sentIntent, deliveryIntent);
                smsManager.sendTextMessage(et_ph.getText().toString(), null, et_msg.getText().toString(), null, null);
                Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}