package com.example.multithread;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar pbar;
    Handler handler;
    TextView tv4;
    Button bt_sleep, bt_start;
    int i;
    EditText et_time;

    @Override
    @TargetApi(21)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_sleep = findViewById(R.id.bt_sleep);
        bt_start = findViewById(R.id.bt_start);
        pbar = findViewById(R.id.pbar);
        tv4=findViewById(R.id.tv4);
        et_time = findViewById(R.id.et_time);

        handler = new Handler();
        pbar.setProgress(0);

        bt_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int count = Integer.parseInt(et_time.getText().toString());
                final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                //pd.setTitle("Sleep in Progress");
                //pd.setMessage("Sleep in Progress");
                pd.show();
                /*handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                }, count * 1000);*/
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                        while(i<=count){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pd.setMessage("Sleeping now for " +(i)+" seconds...");
                                    i++;
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pd.dismiss();
                    }
                }).start();
            }
        });


        bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(29)
            public void onClick(View v) {
                pbar.setProgressTintList(ColorStateList.valueOf(Color.RED));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        i=0;
                        while(i<=100){
                            if (i == 100) {
                                pbar.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        pbar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                                    }
                                });
                            }
                            pbar.setProgress(i);
                            pbar.post(new Runnable() {
                                @Override
                                public void run() {
                                    tv4.setText(String.valueOf(i) + "%");
                                    i++;
                                }
                            });
                            try {
                                Thread.sleep(100);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }
}


