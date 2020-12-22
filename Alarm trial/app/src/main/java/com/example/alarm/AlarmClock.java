package com.example.alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.core.app.NotificationCompat;

public class AlarmClock extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent i1) {

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone=RingtoneManager.getRingtone(context,uri);
        ringtone.play();

        PendingIntent pd=PendingIntent.getActivity(context,0,i1,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentIntent(pd);
        builder.setContentTitle("Alarm");
        builder.setContentText("Wake up");
        builder.setAutoCancel(true);

        NotificationManager manager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }
}
