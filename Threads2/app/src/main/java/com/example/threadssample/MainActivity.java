package com.example.threadssample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar bar;
    Button b, b2;
    TextView t;
    EditText e;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = 0;
        bar = (ProgressBar) findViewById(R.id.progressBar);
        b = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        t = (TextView) findViewById(R.id.textView4);
        e = (EditText) findViewById(R.id.editText);
        bar.setProgress(0);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        i = 0;
                        while (i <=100) {
                            bar.setProgress(i);
                            bar.post(new Runnable() {
                                @Override
                                public void run() {
                                    //bar.getProgressDrawable().setColorFilter(colors[i/25], android.graphics.PorterDuff.Mode.SRC_IN);
                                    t.setText(String.valueOf(i) + "%");
                                    i++;
                                }
                            });
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTask d = new DialogTask(MainActivity.this);
                d.execute(Integer.parseInt(e.getText().toString()));
            }
        });
    }

    class DialogTask extends AsyncTask<Integer, String, Void> {
        ProgressDialog pd;

        public DialogTask(MainActivity c) {
            pd = new ProgressDialog(c);
        }

        @Override
        protected void onPreExecute() {
            pd.show();
        }

        @Override
        protected Void doInBackground(Integer... delay) {
            for(int i=0;i<delay[0];i++)
            try {
                publishProgress("Sleeping now for "+i+1+" seconds...");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String...txt)
        {
            pd.setMessage(txt[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
        }
    }
}