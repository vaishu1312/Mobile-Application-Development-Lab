package com.example.alarm_version1;

import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import java.util.Calendar;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class alarmReceiver extends BroadcastReceiver {

    Uri uri;
    static Ringtone ringtone;

    @RequiresApi(16)
    public void onReceive(Context context, Intent intent) {

        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();

        Intent i1 = new Intent(context, MainActivity.class);
        // i1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pd = PendingIntent.getActivity(context, 0, i1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentIntent(pd);
        builder.setContentTitle("Alarm");
        builder.setContentText("Wake up!!It's " +Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":"+Calendar.getInstance().get(Calendar.MINUTE));
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }
}
