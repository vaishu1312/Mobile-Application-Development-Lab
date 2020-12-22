package com.example.alarmtrial;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class ringtonePlayer {

    private static Ringtone ringtone;
    private static Uri uri;


    public static void getRingtone(Context context) {
        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, uri);
    }

    public static void playRingtone(Context context) {

        if (ringtone == null) {
            //Log.i("alarm","ringtone is null");
            getRingtone(context);
        }
        if (!ringtone.isPlaying())
            ringtone.play();
    }

    public static void stopRingtone() {
        if (ringtone.isPlaying())
            ringtone.stop();
    }

    public static boolean isPlaying() {
        return ringtone.isPlaying();
    }

    public static boolean isNull() {
        return ringtone == null;
    }


}
