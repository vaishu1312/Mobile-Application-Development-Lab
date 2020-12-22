package com.example.ex6notif;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button notify;
    EditText e;
    String channelId = "channelNotif";

    @Override
    @TargetApi(16)

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notify = (Button) findViewById(R.id.button);
        e = (EditText) findViewById(R.id.editText);

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);  //1000L -L denotes long    //System.currentTimeMillis()%10000 can also be done
                //The above line only works with diff seconds. So if you have multiple notifications at the same second, this will not work.
                m += new Random().nextInt(100) + 1;  //hence add this line

                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("msg", e.getText().toString());
                PendingIntent pending = PendingIntent.getActivity(MainActivity.this, m, intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, channelId);
                builder.setSmallIcon(R.mipmap.ic_launcher_round);
                builder.setContentTitle("Thanks for registering");
                builder.setContentText("You've successfully registered. Tap for more....");
                builder.setContentIntent(pending);
                builder.setAutoCancel(true);
                builder.setPriority(NotificationCompat.PRIORITY_MAX); //priority required for api<26
                //for api>=26, importance is set
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {    // Create the NotificationChannel, but only on API 26+ because
                    // the NotificationChannel class is new and not in the support library
                    NotificationChannel channel = new NotificationChannel(channelId, "Notification channel", NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(channel);
                }
                manager.notify(m, builder.build());

            }
        });
    }
}
