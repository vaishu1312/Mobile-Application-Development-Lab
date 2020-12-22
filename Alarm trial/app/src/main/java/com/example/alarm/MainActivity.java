package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NotificationManager nm;
    Notification n;
    TimePicker tp;
    DatePicker dp;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tp = (TimePicker) findViewById(R.id.timePicker1);
        dp = (DatePicker) findViewById(R.id.datePicker1);
        b=findViewById(R.id.button1);

        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        n = new Notification(R.drawable.ic_launcher_background, "ALARM", System.currentTimeMillis());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(23)
            public void onClick(View v) {
                Calendar current = Calendar.getInstance();
                Calendar alarm = Calendar.getInstance();
                alarm.set(dp.getYear(), dp.getMonth(),dp.getDayOfMonth(), tp.getHour(), tp.getMinute());
                if (alarm.compareTo(current) <= 0)
                    Toast.makeText(getApplicationContext(), "Invalid Date and Time !!!", Toast.LENGTH_LONG).show();
                else {

                    AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent myIntent = new Intent(MainActivity.this, AlarmClock.class);
                    PendingIntent pending = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
                    am.set(AlarmManager.RTC_WAKEUP, alarm.getTimeInMillis(), pending);
                    Toast.makeText(getApplicationContext(), "Alarm is Set ON !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
