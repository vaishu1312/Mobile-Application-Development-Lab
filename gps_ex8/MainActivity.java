package com.example.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button getCurrent,getCoordinates;
    TextView latView,longView,lat2,long2;
    EditText addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getCurrent=findViewById(R.id.getCurrent);
        latView=findViewById(R.id.tv_latVal);
        longView=findViewById(R.id.tv_longVal);

        getCoordinates=findViewById(R.id.getCoordinates);
        addressText=findViewById(R.id.addressText);
        lat2=findViewById(R.id.tv_latVal1);
        long2=findViewById(R.id.tv_longVal1);

        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        //The permissions requested by this method must be requested in your manifest, they should not be granted to your app,
        // and they should have protection level dangerous.
        //request code is used along with on public void onRequest
        // PermissionsResult(int requestCode, String[] permissions, int[] grantResults

        getCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GPSTracker tracker=new GPSTracker(getApplicationContext());
                Location l=tracker.getLocation();

                if(l != null) {
                    Double lng = (double) Math.round(l.getLongitude() * 100) / 100;
                    Double lat = (double) Math.round(l.getLatitude() * 100) / 100;
                    latView.setText(lat.toString());
                    longView.setText(lng.toString());
                }
                else
                {
                    latView.setText("00.00");
                    longView.setText("00.00");
                }
            }
        });

        getCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder coder = new Geocoder(getApplicationContext());
                ArrayList<Address> addresses = null;
                try {
                    String address = addressText.getText().toString();
                    addresses = (ArrayList<Address>) coder.getFromLocationName(address, 1);
                    for(Address add: addresses){
                        Double lng = (double) Math.round(add.getLongitude() * 100) / 100;
                        Double lat = (double) Math.round(add.getLatitude() * 100) / 100;
                        lat2.setText(lat.toString());
                        long2.setText(lng.toString());
                    }
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(),"Can't get coordinates!\nNetwork problem",Toast.LENGTH_LONG);
                }
            }
        });
    }
}
