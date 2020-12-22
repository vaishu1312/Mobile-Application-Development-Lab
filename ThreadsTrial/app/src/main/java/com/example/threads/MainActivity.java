package com.example.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button bt,bt1;
public TextView tv;
public static volatile boolean stopFlag=false;
Handler mainHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt=findViewById(R.id.bt);
        bt1=findViewById(R.id.bt1);
        tv=findViewById(R.id.tv);
        /*Runnable myRunnable1 = new MyRunnable();
        Thread t1 = new Thread(myRunnable1);
        t1.start();

        ExampleThread t = new ExampleThread();
        t.start();*/

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopFlag=true;
            }
        });
    }

    public void startThread(View view) {
        stopFlag=false;

        //MyRunnable myRunnable1 = new MyRunnable(tv,mainHandler);
        //new Thread(myRunnable1).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 10; i++){
                        if(i==5)
                        {
                            tv.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setText("good");
                                }
                            });
                        }
                        //bt.setText("val:"+i);
                        Thread.sleep(1000);
                        Log.i("<<runnable>>", "runnable talking: " + i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

