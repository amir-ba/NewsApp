package NewsApp.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MainActivity;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.MyMarkerObj;
import com.example.s1908114.newsapp.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir on 04.12.2015.
 */
public class LocationFragment extends MapFragment implements android.location.LocationListener   {

    SQLiteDatabase database;
    Cursor dbCursor;
    DatabaseHelper dbHelper;

    private LocationManager locationManager;
    private String provider;
    private static GoogleMap mMap;
    private Marker marker;
    public static String[] MenuFilter;
    public static String[] DefaultFilter;
    public  SupportMapFragment mMapFragment;
    final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    final int LOCATION_REQUEST = 1340;
     public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);


         View rootView = inflater.inflate(R.layout.activity_maps, container, false);


      //   MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
         mMap= MainFragment.mMap;
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, (LocationListener) this);


        } else {
             requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, (LocationListener) this);

        }
        // setUpMapIfNeeded();
        return rootView;
    }
    public   void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
                   MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            MapFragment fm1 = (MapFragment) getFragmentManager().findFragmentByTag("Fragment1");
            MapFragment ff = (MapFragment) getFragmentManager().findFragmentByTag("Frahment1");
          //   mMap =  getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }
    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        if (mMap != null)
            setUpMap();

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            MapFragment mf = new MainFragment();
            mMap =  getMap();
          //  SupportMapFragment gg = (SupportMapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
            //mMap = gg.getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null)
                setUpMap();
        }
    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/


    @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            double lat =  location.getLatitude();
            double lng = location.getLongitude();
          //  MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
          //  MapFragment fm1 = (MapFragment) getFragmentManager().findFragmentByTag("Fragment1");
         //   MapFragment mf = new MainFragment();
         //   MapFragment gg = (MapFragment) mf.getFragmentManager().findFragmentById(R.id.map);

            mMap.clear(); //Remove all marker from map before refresh it.

            LatLng latLng = new LatLng(lat, lng);
            LatLng coordinate = new LatLng(lat, lng);


            Marker marker = mMap.addMarker(new MarkerOptions().position(coordinate).title("Title").snippet("Snippet")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location)));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(5));
            Toast.makeText(getActivity(), "Location " + lat+","+lng,Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(), "Location " + coordinate.latitude+","+coordinate.longitude,Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Location off",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity(), "Enabled new provider " + provider,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity(), "Disabled provider " + provider,Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

}
/*
    @Override
    public void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        dbHelper = new DatabaseHelper(this.getActivity());
        mMap = googleMap;
        setUpMap();
    }

    public void markerFilter() {
        DefaultFilter = new String[]{"Politics", "Business", "Sport", "Science and Technology", "National","Education"};

        if (MenuFilter == null) {
            MenuFilter = DefaultFilter;
        } else {

            Bundle mBundle = new Bundle();
            mBundle = getArguments();
            MenuFilter = mBundle.getStringArray("key");
        }

    }

    private void setUpDatabase() {
        try {
            markerFilter();
            dbHelper.createDataBase();

        } catch (IOException ioe) {
            Log.e("heloH", "heloH");
        }
        List<MyMarkerObj> markers = new ArrayList<MyMarkerObj>();
        try {
            database = dbHelper.getDataBase();
            String query = " SELECT   NewsTable.id," +
                    "  NewsTable.category as category ,NewsTable.headline as headline, NewsTable.lat as lat ,NewsTable.lon, NewsTable.maintext ,NewsTable.place, NewsTable.dates , CapitalsTable.code  \n" +
                    "            FROM  NewsTable   JOIN CapitalsTable\n" +
                    "            ON NewsTable.statecode=CapitalsTable.code   "
                    + " where NewsTable.category=? or NewsTable.category=?" +
                    " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";
            //      MenuFilter= new String[] {"Politics","Business","Sport","Science & Technology"};
            dbCursor = database.rawQuery(query, MenuFilter);
            // query(DatabaseHelper.TABLE_NAME,, cols,where,null,null, null, null);
            //     MenuFilter=null;
            dbCursor.moveToFirst();


            while (!dbCursor.isAfterLast()) {
                MyMarkerObj m = new MyMarkerObj();

                m.setTitle(dbCursor.getString(1));
                m.setSnippet(dbCursor.getString(2));
                m.setLat(dbCursor.getString(3));
                m.setLon(dbCursor.getString(4));

                markers.add(m);
                dbCursor.moveToNext();
            }


            for (int i = 0; i < markers.size(); i++) {

                //      LatLng latlon = new LatLng(Double.valueOf(markers.get(i).getLat()),Double.valueOf(markers.get(i).getLon()));
                MyItem offsetItem = new MyItem(Double.valueOf(markers.get(i).getLat()), Double.valueOf(markers.get(i).getLon()), markers.get(i).getTitle(), markers.get(i).getSnippet());
                System.out.println(offsetItem.getPosition());

                mMap.addMarker(new MarkerOptions().title(offsetItem.getTitle()).snippet(offsetItem.getSnippet())
                                .position(offsetItem.getPosition())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location))
                );
            }


        } finally

        {
            if (database != null) {
                dbHelper.close();
            }
        }

    }

    private void setUpMap() {

        try {

            KmlLayer kmlLayer = new KmlLayer(getMap(), R.raw.germ, getActivity().getApplicationContext());
            kmlLayer.addLayerToMap();
            //     moveCameraToKml(kmlLayer);
            mMap.setMyLocationEnabled(true);

            mMap.getUiSettings().

                    setMapToolbarEnabled(false);

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().

                    setMyLocationButtonEnabled(true);

            mMap.getUiSettings().

                    setZoomControlsEnabled(true);


            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(50.669155, 10.746248), 5));


            LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean enabledGPS = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

            setUpDatabase();
        } catch (Exception e) {
            Log.e("Exception caugh eeet", e.toString());
        }

        /* PART FOR THE MARKER ADDING FROM DATABASE AND GPS ENABLING CHECK
        LocationManager ln =(LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        String provider = ln.getBestProvider(new Criteria( ),true);
        if (provider==null)
        {
           onProviderDisabled(provider);
        }
        Location loc = ln.getLastKnownLocation(provider);
        if(loc== null){
           onLocationChanged(loc)
        }

        try {
            dbHelper.createDataBase();
        } catch (IOException ioe) {
        }

        try {
            database = dbHelper.getDataBase();
            dbCursor = database.rawQuery("SELECT lat FROM News;", null);
            dbCursor.moveToFirst();
            int index = dbCursor.getColumnIndex("lat");
            while (!dbCursor.isAfterLast()) {
                String record = dbCursor.getString(index);
                list_valuesLat.add(record);
                dbCursor.moveToNext();
            }

            dbCursor = database.rawQuery("SELECT lon FROM News;", null);
            dbCursor.moveToFirst();
            int index1 = dbCursor.getColumnIndex("lon");
            while (!dbCursor.isAfterLast()) {
                String record = dbCursor.getString(index);
                list_valuesLon.add(record);
                dbCursor.moveToNext();
            }

        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, list_valuesLat);
         setListAdapter(adapter);
*/
/*

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()

                                   {

                                       @Override
                                       public void onMapClick(LatLng point) {

                                           //remove previously placed Marker
                               //            if (marker != null) {
                              //                 marker.remove();
                              //             }

                                           //place marker where user just clicked
                             //              marker = mMap.addMarker(new MarkerOptions().position(point).title("Marker")
                            //                       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

                                       }
                                   }

        );

    }

 
}

*/