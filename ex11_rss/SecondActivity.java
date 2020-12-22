package com.example.final_ex6;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ArrayList<RssItem> list;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        lv = findViewById(R.id.lv);

        Intent i1 = getIntent();
        list = (ArrayList<RssItem>) i1.getSerializableExtra("feed");

        ArrayAdapter adapter = new ArrayAdapter<RssItem>(this, R.layout.list_view, list);
        lv.setAdapter(adapter);


    }
}