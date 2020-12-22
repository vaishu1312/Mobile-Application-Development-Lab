package com.example.ex9_sdcard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    EditText et_content,et_name;
    Button bt_write,bt_read;
    TextView tv_info;
    String fileName,content;
    File myFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_info=findViewById(R.id.tv_info);
        et_name=findViewById(R.id.et_name);
        et_content=findViewById(R.id.et_content);

        bt_write=findViewById(R.id.bt_write);
        bt_read=findViewById(R.id.bt_read);

        if (!isExternalStorageMounted()) {
            bt_read.setEnabled(false);
            bt_write.setEnabled(false);
            tv_info.setText("External Strorage not mounted");
        } else if (isExternalStorageReadOnly()) {
            bt_write.setEnabled(false);
            tv_info.setText("External Strorage is read-only! Can't write data to SD Card");
        }
        /*From API 23 runtime permissions are included. Prior to 23 all permissions were granted during installation itself.
         Since from API level 19 i.e;KITKAT, WRITE_EXTERNAL_STORAGE permission is not required to read/write files in your application-specific
         directories returned by Context.getExternalFilesDir(String) and Context.getExternalCacheDir(), it is not checked if
         permission has been granted or not, as permission is not necessary for the given operation.*/

        /*I don't require the permission after kitkat and before kitkat all permissions are granted during install time and
        can't be revoked later and hence need not be checked and requested explicity*/

        bt_write.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(21)
            public void onClick(View v) {
                fileName=et_name.getText().toString().trim();
                content=et_content.getText().toString().trim();
                tv_info.setText("");
                if(fileName.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter file name", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    //File myFile = new File(getExternalFilesDir(null), fileName);
                    File[] farr = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
                    if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.KITKAT_WATCH) {
                        myFile = new File(farr[0], fileName);
                    }
                    else {
                        for(File file:farr)
                            if(Environment.isExternalStorageRemovable(file)) {
                                myFile = new File(file, fileName);
                                break;
                            }
                    }
                   // Log.i("space", "before: " + myFile.getUsableSpace());
                    FileOutputStream fos = new FileOutputStream(myFile);
                    fos.write(content.getBytes());
                    fos.close();
                    Toast.makeText(MainActivity.this, "File "+fileName+" written to SD card", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error writing "+fileName+" to SD card"+e.getMessage(), Toast.LENGTH_LONG).show();
                }
                finally {
                    et_name.setText("");
                    et_content.setText("");
                }
            }
        });

        bt_read.setOnClickListener(new View.OnClickListener() {
            @Override
            @TargetApi(21)
            public void onClick(View v) {
                tv_info.setText("");
                et_content.setText("");
                fileName=et_name.getText().toString().trim();
                if(fileName.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Please enter file name", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    File[] farr = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
                    if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.KITKAT_WATCH) {  //api 18
                        myFile = new File(farr[0], fileName);
                    }
                    else {
                        for(File file:farr)
                            if(Environment.isExternalStorageRemovable(file)) {
                                myFile = new File(file, fileName);
                                break;
                            }
                    }
                    BufferedReader br = new BufferedReader(new FileReader(myFile));
                    String input, text = "";
                    while ((input = br.readLine()) != null)
                        text = text+input+"\n";
                    br.close();

                    tv_info.setText("Content read from file "+fileName+": \n"+text);
                    /*Scanner r = new Scanner(myFile);
                    r.useDelimiter("\\Z");
                    tv_info.setText("Content read from file "+fileName+": \n"+r.next());*/
                    Toast.makeText(MainActivity.this, "File "+fileName+" read", Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error reading "+fileName+" from SD card: No such file", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private boolean isExternalStorageMounted() {
        //Don't use == for this comparison ; Environment.MEDIA_MOUNTED is equal to 'mounted'
        return (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ) ||
                (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY ));
    }

    private boolean isExternalStorageReadOnly() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
            {
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_DENIED)
                break;
            }
        }
    } */
}
