package com.example.alarm_version1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Calendar calObj,current;
    EditText et_time;
    Button bt_set,bt_stop;
    AlarmManager alarmManager;
    PendingIntent pending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_time=findViewById(R.id.et_time);
        bt_set=findViewById(R.id.bt_set);
        bt_stop=findViewById(R.id.bt_stop);

        calObj =Calendar.getInstance();

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pending);
                et_time.setText("");
                if(alarmReceiver.ringtone!=null)
                    alarmReceiver.ringtone.stop();
            }
        });

        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(19)
            public void onClick(View v) {

                if(!et_time.getText().toString().equals(""))
                {
                    //Log.i("TIME", " val1: "+calObj.compareTo(Calendar.getInstance()));
                    //Log.i("TIME","set: "+calObj.getTime()+" cur: "+Calendar.getInstance().getTime());

                    if (calObj.compareTo(Calendar.getInstance()) <= 0)
                        Toast.makeText(getApplicationContext(), "Invalid Time !!!", Toast.LENGTH_LONG).show();
                    else
                    {
                        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Intent myIntent = new Intent(MainActivity.this, alarmReceiver.class);
                        pending = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calObj.getTimeInMillis(), pending);
                        double time=calObj.getTimeInMillis()-Calendar.getInstance().getTimeInMillis();
                        time=Math.ceil(time/(60*1000));
                        Toast.makeText(MainActivity.this, "Alarm set for "+(int)time+" minutes from now!",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "Please select the time",Toast.LENGTH_LONG).show();
            }
        });

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                et_time.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));

                                calObj.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calObj.set(Calendar.MINUTE, minute);
                                calObj.set(Calendar.SECOND,0);

                            }
                        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
    }
}
