package com.mdnahid.findlocationusingservice;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Map extends AppCompatActivity {
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setup();
    }
    private void setup() {
        if(mMap==null)
        {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }
        if(mMap!=null)
        {
            setupMap();
        }
    }
    private void setupMap() {

        try {
            FileInputStream fis = new FileInputStream(new File("/sdcard/map.txt"));
            if(fis!=null){
                InputStreamReader reader = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String receivedText = "";
                ArrayList<String> latLag = new ArrayList<String>();
                while ((receivedText = bufferedReader.readLine()) != null) {
                    latLag.add(receivedText);
                }
                fis.close();



                // map set
                int len=latLag.size();
                if(len>1) {
                    PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                    for (int i = 1; i < latLag.size()-1; i = i + 2) {
                        String l = latLag.get(i).trim();
                        String l1 = latLag.get(i+1).trim();
                        double a=Double.parseDouble(l);
                        double b=Double.parseDouble(l1);
                        options.add(new LatLng(a, b));

                        mMap.addMarker(new MarkerOptions().position(new LatLng(a, b)));
                    }


                    mMap.addPolyline(options);

                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latLag.get(1)), Double.parseDouble(latLag.get(2)))).title("start point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).snippet("start")).showInfoWindow();
                    if(len>4)
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latLag.get(len - 2)), Double.parseDouble(latLag.get(len - 1)))).title("end point").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latLag.get(1)), Double.parseDouble(latLag.get(2))), 17));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

                }
                else{
                    Toast.makeText(Map.this, "Data not avaliable", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(Map.this,"File Not found",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Map.this,e.toString(),Toast.LENGTH_SHORT).show();
            Log.i("Error", " " + e.toString());
        }

    }
}
