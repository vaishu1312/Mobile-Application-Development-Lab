package com.example.ex10_smsalert;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;

import java.util.Date;
import java.util.Random;

public class Receiver extends BroadcastReceiver {
    String channelId = "channelAlarm";
    SmsMessage smsMessage;

    @Override

    public void onReceive(Context context, Intent intent) {

        String no = null, msg = null;
        long time = 0;
        Person p = new Person.Builder().setKey("123").setName("You").build(); //receiver
        Person sender = null;
        Bundle b;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //KITKAT
            SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
            smsMessage = msgs[0];
        }
        else {  //intent has extras: "pdus" - An Object[] of byte[]s containing the PDUs that make up the message.
            b = intent.getExtras();
            if (b != null) {
                Object pdus[] = (Object[]) b.get("pdus");
                smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);

            }
            else
                return;
        }

        no =smsMessage.getOriginatingAddress();
        msg = smsMessage.getMessageBody();
        time = smsMessage.getTimestampMillis();
        //sm.getUserData();
        sender = new Person.Builder().setKey(no).setName(no).build();

        if(InboxActivity.active) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InboxActivity.instance().refreshInbox();
        }
            sendNotification(context, p, msg, sender, time);
    }

    public void sendNotification(Context context,Person p, String msg, Person sender,long time) {

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);  //1000L -L denotes long    //System.currentTimeMillis()%10000 can also be done
        //The above line only works with diff seconds. So if you have multiple notifications at the same second, this will not work.
        m += new Random().nextInt(100) + 1;  //hence add this line

        Intent i1 = new Intent(context,InboxActivity.class);    //
        i1.putExtra("from","receiver");
        PendingIntent pd = PendingIntent.getActivity(context, m, i1, 0);    //

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        //builder.setContentTitle("Message!!"); //from name //  title and text will be ignored if style is messaging
        //builder.setContentText("FROM: " + no + "\n" + msg); //subject \n won't work to display multi line text in notif use big style
        builder.setContentIntent(pd);
        builder.setAutoCancel(true);
        builder.setStyle(new NotificationCompat.MessagingStyle(p).addMessage(msg, time, sender));
        //builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Much longer text that cannot fit one line..."));
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {    // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            NotificationChannel channel = new NotificationChannel(channelId, "Notification channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            //setChannelid
        }
        manager.notify(m, builder.build());  //NotificationCompat.Builder.build() returns a Notification
    }

}