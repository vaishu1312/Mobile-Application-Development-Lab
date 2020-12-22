package com.example.final_ex6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {
    TextView tv;
    EditText et_url;
    Button bt_fetch;
    String finalUrl = "https://www.thehindu.com/news/national/feeder/default.rss";
    //String finalUrl = "https://tutorialspoint.com/android/sampleXML.xml";
    HandleXML obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textview);
        et_url = (EditText) findViewById(R.id.et_url);
        bt_fetch = (Button) findViewById(R.id.bt_fetch);

        //Internet permission is a normal one not dangerous hence will be granted automatically during install time

        bt_fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!haveNetworkConnection()) {
                    Toast.makeText(MainActivity.this, "Internet connection not available\nPlease connect to internet!!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!et_url.getText().toString().equals(""))
                    finalUrl = et_url.getText().toString();
                obj = new HandleXML(finalUrl, MainActivity.this);
                obj.fetchXML();
                while (!obj.parsingComplete) ;
                ArrayList<RssItem> list = obj.getList();
                Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
                myIntent.putExtra("feed", list);
                startActivity(myIntent);
            }
        });
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}