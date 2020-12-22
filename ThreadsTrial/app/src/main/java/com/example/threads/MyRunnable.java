package com.example.threads;

import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import android.os.Handler;

public class MyRunnable implements Runnable {

   TextView tv;
   Handler h;

    public MyRunnable(TextView tv,Handler h)
    {
        this.tv=tv;
        this.h=h;
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++){
                if(MainActivity.stopFlag)
                    return;
               if(i==5)
               {
                   /*Handler threadHandler =new Handler(Looper.getMainLooper());
                   //h.post(new Runnable() {
                   threadHandler.post(new Runnable() {
                       @Override
                       public void run() {
                           tv.setText("good");
                       }
                   });*/
                   tv.post(new Runnable() {
                       @Override
                       public void run() {
                           tv.setText("good");
                       }
                   });
               }
                Thread.sleep(1000);
                Log.i("<<runnable>>", "runnable talking: " + i);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//run
}//class