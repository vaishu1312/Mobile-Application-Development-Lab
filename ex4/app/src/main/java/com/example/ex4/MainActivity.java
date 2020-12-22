package com.example.ex4;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
public class MainActivity extends AppCompatActivity {
    int x=0, y=0, z=0;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_paint=findViewById(R.id.bt_paint);
        Button bt_animate=findViewById(R.id.bt_animate);
        Button bt_car=findViewById(R.id.bt_car);
        Button bt_forward=findViewById(R.id.bt_forward);
        Button bt_backward=findViewById(R.id.bt_backward);
        final ImageView iv_animate=findViewById(R.id.iv_animate);

        bt_paint.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), paint.class);
                startActivity(myIntent);
            }
        });

        bt_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_animate.animate().translationXBy(300f).setDuration(600);
            }
        });

        bt_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_animate.animate().translationXBy(-300f).setDuration(600);
            }
        });

        bt_animate.setOnClickListener(new View.OnClickListener() {
            int var2=0;
            @Override
            public void onClick(View v) {
                if(var2==0) {
                    iv_animate.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in));
                    var2=1;
                }
                else if(var2==1){
                    iv_animate.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in));
                    var2=2;
                }
                else
                {
                    iv_animate.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_in));
                    var2=0;
                }
            }
        });

        bt_car.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (z % 3 == 0) {
                    iv_animate.setBackgroundResource(R.drawable.car_img);
                    z++;
                }
                else if (z % 3 == 1) {
                    iv_animate.setBackgroundResource(R.drawable.car_img1);
                    z++;
                }
                if (z % 3 == 2) {
                    iv_animate.setBackgroundResource(R.drawable.car_img2);
                    z++;
                }
            }
        });
    }
}
