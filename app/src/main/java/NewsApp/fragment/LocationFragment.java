package NewsApp.fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.R;
import com.example.s1908114.newsapp.SideBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;


/**
 * Created by Amir on 04.12.2015.
 */
//THIS FRAGMENT WILL INITIATE THE LOCATION SHOWER ON THE MAP AND DISAGGREGATION OF THE CLUSTERS
public class LocationFragment extends Fragment {

    SQLiteDatabase database;
    Cursor dbCursor;
    DatabaseHelper dbHelper;
    private LocationManager locationManager;
    private static GoogleMap mMap;
    final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    public String query = " SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline," +
            " NewsTable.lat as lat ,NewsTable.lon as lon,NewsTable.place, NewsTable.maintext , NewsTable.dates , category ,image \n" +
            "            FROM  NewsTable    "
            + "where NewsTable.category=? or NewsTable.category=?" +
            " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";
    final int LOCATION_REQUEST = 1340;

    // INITIATE
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // CREATE Placeholder view
        View rootView = inflater.inflate(R.layout.text_bubble, container, false);
        //get the map
        mMap = MainFragment.mMap;
        mMap.setMyLocationEnabled(true);
        mMap.clear();
        CameraUpdateFactory.zoomTo(7);
        //define a location listener and use it
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        MainFragment.mClusterManager.setRenderer(new MyclusterRenderer2(getActivity().getApplicationContext(), mMap, MainFragment.mClusterManager));
        //set a click listener for the markers
        mMap.setOnMarkerClickListener(MainFragment.mClusterManager);
        // request location
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 10, mlocListener);

        } else {
            this.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        }

        return rootView;
    }

    // location listener to zoom to the your surrounding and show articles on city level
    private LocationListener mlocListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                mMap.setMyLocationEnabled(true);
                mMap.clear();
                MainFragment.mMap.clear();
                MainFragment.mClusterManager.clearItems();
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                // stop listening for location
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.removeUpdates(mlocListener);
                    locationManager = null;
                }
                // draw a circle around your vicinity
                Location loc = new Location("L");
                LatLng latLng = new LatLng(lat, lng);
                Circle circle = mMap.addCircle(new CircleOptions().center(latLng).radius(20 * 1000).fillColor(Color.parseColor("#3303A9F4")).strokeColor(Color.parseColor("#3303A9F4")));
                // get the markers for all the cities from database and show them on the map
                setUpDatabase(query, loc);
                // zoom to location area
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));

            } else {
                Toast.makeText(getActivity(), "Location off", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        // alert to activate location if not activated
        public void onProviderDisabled(String provider) {
            Toast.makeText(getActivity(), "Please Enabled your " + provider, Toast.LENGTH_SHORT).show();

        }


        @Override
        //alert when location enabled
        public void onProviderEnabled(String provider) {
            Toast.makeText(getActivity(), "Enabled provider " + provider, Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }

    };

    // function to query the database and add them to map
    private void setUpDatabase(String qu, Location loc) {
        try {
            //open the database
            dbHelper = new DatabaseHelper(this.getActivity());
            database = dbHelper.getDataBase();
            // query from database
            dbCursor = database.rawQuery(qu, SideBar.MenuFilter);
            dbCursor.moveToFirst();
            // go through the database add them to clusters
            while (!dbCursor.isAfterLast()) {
                MyItem m = new MyItem(Double.valueOf(this.dbCursor.getString(3))
                        , Double.valueOf(this.dbCursor.getString(4)),
                        (this.dbCursor.getString(1)),
                        (this.dbCursor.getString(2)),
                        this.dbCursor.getString(5),
                        this.dbCursor.getString(6),
                        this.dbCursor.getString(7),
                        this.dbCursor.getString(8),
                        this.dbCursor.getString(9));

                MainFragment.mClusterManager.addItem(m);

                dbCursor.moveToNext();
            }

            // close database if not needed
        } finally

        {
            if (database != null) {
                dbHelper.close();
            }
        }


    }


}

