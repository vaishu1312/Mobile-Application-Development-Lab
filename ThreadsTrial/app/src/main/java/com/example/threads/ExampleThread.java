package com.example.threads;

import android.util.Log;

public class ExampleThread extends Thread {

    public void run(){
        for(int i=0;i<10;i++)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("MainACtivity: ","Thread start"+i);
        }
    }

}
