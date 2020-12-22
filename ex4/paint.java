package com.example.ex4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class paint extends AppCompatActivity {

    LinearLayout ll2;
    ImageView img_draw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        Button bt_line=(Button)findViewById(R.id.bt_line);
        Button bt_circle=(Button)findViewById(R.id.bt_circle);
        Button bt_rect=(Button)findViewById(R.id.bt_rect);
        Button bt_arc=(Button)findViewById(R.id.bt_arc);
        ll2=findViewById(R.id.ll2);
        img_draw=findViewById(R.id.img_draw);


        bt_rect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Bitmap  b= Bitmap.createBitmap(720,1280, Bitmap.Config.ARGB_8888);
            img_draw.setBackgroundDrawable(new BitmapDrawable(b));
            Paint p=new Paint();
            p.setStrokeWidth(10);
            Canvas canvas=new Canvas(b);
            p.setColor(Color.GREEN);
            RectF r = new RectF(0, 0, 400, 200);
            canvas.drawRect(r,p);
        }
        });

        bt_arc.setOnClickListener(new View.OnClickListener() {
            @TargetApi(21)
            @Override
            public void onClick(View v) {
                Bitmap b= Bitmap.createBitmap(720,1280, Bitmap.Config.ARGB_8888);
                img_draw.setBackgroundDrawable(new BitmapDrawable(b));
                Paint p=new Paint();
                Canvas canvas=new Canvas(b);
                p.setStrokeWidth(10);
                p.setColor(Color.RED);
                canvas.drawArc(200, 200, 600, 600, 20, 115, true, p);

            }
        });

        bt_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b= Bitmap.createBitmap(720,1280, Bitmap.Config.ARGB_8888);
                img_draw.setBackgroundDrawable(new BitmapDrawable(b));
                Paint p=new Paint();
                Canvas canvas=new Canvas(b);
                p.setStrokeWidth(10);
                p.setColor(Color.BLUE);
                canvas.drawLine(200, 200, 600, 600, p);

            }
        });

        bt_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b= Bitmap.createBitmap(720,1280, Bitmap.Config.ARGB_8888);
                img_draw.setBackgroundDrawable(new BitmapDrawable(b));
                Paint p=new Paint();
                Canvas canvas=new Canvas(b);
                p.setStrokeWidth(10);
                p.setColor(Color.YELLOW);
                canvas.drawCircle(400, 400, 200, p);

            }
        });

    }
}
