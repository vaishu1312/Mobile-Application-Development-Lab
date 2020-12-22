package com.example.alarmtrial;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Calendar calObj;
    EditText et_time;
    Button bt_set, bt_stop;
    AlarmManager alarmManager;
    Intent myIntent;

    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_time = findViewById(R.id.et_time);
        bt_set = findViewById(R.id.bt_set);
        bt_stop = findViewById(R.id.bt_stop);

        calObj = Calendar.getInstance();

        myIntent = new Intent(MainActivity.this, alarmReceiver.class);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent i = getIntent();
        Calendar recvCal = (Calendar) i.getSerializableExtra("cal");
        if (recvCal != null)
            et_time.setText(recvCal.get(Calendar.HOUR_OF_DAY) + ":" + recvCal.get(Calendar.MINUTE));

        bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int requestCode;
                if (!et_time.getText().toString().equals("")) {
                    String[] time = et_time.getText().toString().split(":");
                    requestCode = (Integer.parseInt(time[0]) * 100) + Integer.parseInt(time[1]);
                    //Log.i("alarm", "stop: " + requestCode);
                    PendingIntent p = PendingIntent.getBroadcast(MainActivity.this, requestCode, myIntent, PendingIntent.FLAG_NO_CREATE);
                    if (p == null)
                        Toast.makeText(MainActivity.this, "No alarm scheduled for " + time[0] + ":" + time[1], Toast.LENGTH_LONG).show();
                    else {
                        alarmManager.cancel(p);
                        p.cancel();
                        if (!ringtonePlayer.isNull())  //incase alarm is stoppped before its trigerred ringtone would be null;
                            ringtonePlayer.stopRingtone();
                        Toast.makeText(MainActivity.this, "Alarm for " + time[0] + ":" + time[1] + " stopped", Toast.LENGTH_SHORT).show();
                    }
                    et_time.setText("");
                } else
                    Toast.makeText(MainActivity.this, "Please select the time to stop alarm", Toast.LENGTH_LONG).show();
            }
        });

        bt_set.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(19)
            public void onClick(View v) {

                if (!et_time.getText().toString().equals("")) {
                    //Log.i("TIME", " val1: "+calObj.compareTo(Calendar.getInstance()));
                    //Log.i("TIME","set: "+calObj.getTime()+" cur: "+Calendar.getInstance().getTime());

                    if (calObj.compareTo(Calendar.getInstance()) <= 0)
                        Toast.makeText(getApplicationContext(), "Invalid Time !!!", Toast.LENGTH_LONG).show();
                    else {
                        //alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        //myIntent = new Intent(MainActivity.this, alarmReceiver.class);
                        myIntent.putExtra("cal", calObj);
                        // It returns an pending intent that does the work of broadcasting the intent(i.e; here myIntent)
                        int requestCode = (calObj.get(Calendar.HOUR_OF_DAY) * 100) + calObj.get(Calendar.MINUTE);
                        PendingIntent pending = PendingIntent.getBroadcast(MainActivity.this, requestCode, myIntent, 0);
                        //flag 0; if the same pending intent already exists then it is retrieved else new one is created.
                        /*  justanapplication.wordpress.com/tag/flag_cancel_current/#PendingIntentMatching
                        PendingIntents ,match only if request code,intent (equality defined by Intent.filterEquals()) ,flags,function .i.e; getActi/getBroad are all same
                        In flags FLAG_CANCEL_CURRENT  FLAG_NO_CREATE, and FLAG_UPDATE_CURRENT are excluded.
                         */
                        /*above method is similar to sendBroadcast(Intent).
                        The intent obj in sendBroadcast is Intent i=new Intent().
                        Then i.setAction("com.example.Broadcast") this identifies the broadcast event.
                        setAction can be custom are existing like ACTION_ANSWER...
                        https://www.techotopia.com/index.php/Android_Broadcast_Intents_and_Broadcast_Receivers*/

                        /*Registering receiver in code:  This is preferred to registering in manifest
                        If you want your App to listen only for certain instance(When App is running) then go for this Dynamic registration.
                        IntentFilter filter = new IntentFilter("com.example.Broadcast"); //same as i.setAction("")
                        MyReceiver receiver = new MyReceiver();
                        registerReceiver(receiver, filter);*/

                        /*The intent obj in sendBroadcast is Intent i=new Intent().
                        But here intent obj defines context and destination ...There are no intent filters in both
                        manifest and code.
                        When there are no intent filters, the receiver is trigerred when ever there
                        is a broadcast with the intent object contain it(the broadcast receiver) as the the
                        destination.Like this  (explicit broadcast receiver)
                        Intent myIntent = new Intent(MainActivity.this, alarmReceiver.class);
                        */

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calObj.getTimeInMillis(), pending);
                        double time = calObj.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                        time = Math.ceil(time / (60 * 1000));  //MILLISEC to MINS
                        Toast.makeText(MainActivity.this, "Alarm set for " + (int) time + " minutes from now!",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Please select the time to set alarm", Toast.LENGTH_LONG).show();
            }
        });

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                et_time.setText(hourOfDay + ":" + minute);

                                /*if(hourOfDay<12 || (hourOfDay==12 && minute==0))
                                    et_time.setText(hourOfDay + ":" +minute+" AM");
                                else if(hourOfDay==12)
                                    et_time.setText(hourOfDay + ":" + minute+" PM");
                                else
                                    et_time.setText(hourOfDay-12 + ":" + minute+" PM");*/

                                calObj.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calObj.set(Calendar.MINUTE, minute);
                                calObj.set(Calendar.SECOND, 0);

                            }
                        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), false);
                timePickerDialog.show();
            }
        });
    }
}
