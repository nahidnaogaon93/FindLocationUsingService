package com.mdnahid.findlocationusingservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File myFile = new File("/sdcard/map.txt");
        if (!myFile.exists())
        {

            try {
                myFile.createNewFile();
                Toast.makeText(MainActivity.this, "File Crate", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(MainActivity.this,""+e.toString(),Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        else{
            //Toast.makeText(this,"file exists",Toast.LENGTH_SHORT).show();
        }
    }
    public void start(View view){
        startService(new Intent(this, OurService.class));
    }
    public void stop(View view){
        stopService(new Intent(this, OurService.class));
    }
    public void showMap(View view){
        startActivity(new Intent(this, Map.class));
    }
}
