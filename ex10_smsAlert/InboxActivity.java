package com.example.ex10_smsalert;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InboxActivity extends AppCompatActivity {

    public static boolean active = false;
    public static InboxActivity inst;
    TextView msgs;

    public static InboxActivity instance() {
        return inst;
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
        inst = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
        inst = null;
    }

    public void onBackPressed() {
        Intent myIntent = getIntent();
        String str = myIntent.getStringExtra("from");
        if (str != null && str.equals("receiver")) {
            if (ComposeActivity.active) {
                finish();
                this.startActivity(new Intent(InboxActivity.this, ComposeActivity.class));
            } else {
                finish();
                this.startActivity(new Intent(InboxActivity.this, MainActivity.class));
            }
        } else
            super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_inbox);
        inst = this;
        refreshInbox();
    }

    void refreshInbox() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_inbox);
                msgs = findViewById(R.id.msgs);
                String msg = "";
                ContentResolver cr = getContentResolver();
                Cursor i = cr.query(Uri.parse("content://sms/inbox"), new String[]{"address", "body"}, null, null, null);
                if (!i.moveToFirst()) {
                    return;
                } else {
                    do {
                        msg = msg + "From: " + i.getString(i.getColumnIndex("address")) + "\nMessage: " + i.getString(i.getColumnIndex("body")) + "\n\n";
                    } while (i.moveToNext());
                    msgs.setText(msg);
                }
            }
        });
    }
}
