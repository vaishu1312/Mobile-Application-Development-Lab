package com.example.alarmtrial;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class alarmReceiver extends BroadcastReceiver {

    String channelId = "channelAlarm";

    public void onReceive(Context context, Intent intent) {

        ringtonePlayer.playRingtone(context);

        Calendar recvCal = (Calendar) intent.getSerializableExtra("cal");
        //Log.i("alarm", "recv: " + recvCal.get(Calendar.HOUR_OF_DAY) + recvCal.get(Calendar.MINUTE));
        Intent i1 = new Intent(context, MainActivity.class);
        i1.putExtra("cal", recvCal);

        PendingIntent pd = PendingIntent.getActivity(context, 0, i1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Alarm");
        int am_pm = Calendar.getInstance().get(Calendar.AM_PM);   //1=PM, 0=AM
        if (am_pm == 1)
            builder.setContentText("Wake up!! It's " + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + " PM");
        else
            builder.setContentText("Wake up!! It's " + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + " AM");
        builder.setContentIntent(pd);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX); //priority required for api<26
        //for api>=26, importance is set
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {    // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            NotificationChannel channel = new NotificationChannel(channelId, "Notification channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(1, builder.build()); //If a notification with the same id has already been posted by your application
                                                // and has not yet been canceled, it will be replaced by the updated information.
                                                //hence if there's a previous alarm notification that's not been cancelled it is replaced with current one.
    }
}
