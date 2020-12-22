package com.example.ex10_smsalert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    Button bt_send, bt_view;
    TextView tv_err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_send = findViewById(R.id.bt_send);
        bt_view = findViewById(R.id.bt_view);
        tv_err = findViewById(R.id.tv_err);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        String info_text = "";
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        bt_send.setEnabled(false);
                        info_text = "Permission required to send sms\n";
                    }
                    if (grantResults[1] == PackageManager.PERMISSION_DENIED) {
                        bt_view.setEnabled(false);
                        info_text += "Permission required to read sms\n";
                    }
                    if (grantResults[2] == PackageManager.PERMISSION_DENIED) {
                        bt_view.setEnabled(false);
                        info_text += "Permission required to receive sms\n";
                    }
                    if (!info_text.equals(""))
                        info_text += "Please provide the required permissions!!\n";
                    tv_err.setText(info_text);
                }
            }
        }
    }

    public void launchSend(View view) {
        Intent intent = new Intent(MainActivity.this, ComposeActivity.class);
        startActivity(intent);
    }

    public void launchInbox(View view) {
        Intent intent = new Intent(MainActivity.this, InboxActivity.class);
        intent.putExtra("from", "main");
        startActivity(intent);
    }
}
