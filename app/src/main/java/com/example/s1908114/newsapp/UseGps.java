package com.example.s1908114.newsapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Amir on 05.12.2015.
 */
public class UseGps extends Activity implements LocationListener {
    public TextView latitudeField ,
                       lonitudeField;

    LocationManager locationManager; final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    final int LOCATION_REQUEST = 1340;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);
        } else { requestPermissions(LOCATION_PERMS, LOCATION_REQUEST); }
    }

        public void onLocationChanged(Location location) {
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                updateUI(lat,lon);


            } }


        public void updateUI(double lat, double lon){
       //     latitudeField= (TextView) findViewById(R.id.textView01);
       //     latitudeField.setText("Latitude: " + lat);
            GoogleMap map =((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();
            MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon));
            map.addMarker(marker);
             CameraPosition cameraPosition = new CameraPosition.Builder()
                      .target(new LatLng(lat, lon)).zoom(10).build();
             map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

        }
        public void onProviderDisabled(String provider) {
        }
         public void onProviderEnabled(String provider) {
         }
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case LOCATION_REQUEST:
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 10000, 10, this);
                    } else { Toast.makeText(this, "Location cannot be obtained due to missing permission.", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
}
