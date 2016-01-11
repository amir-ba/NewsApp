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
import android.widget.Toast;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.NewsListViewFromMap;
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
public class LocationFragment extends Fragment  {

    SQLiteDatabase database;
    Cursor dbCursor;
    DatabaseHelper dbHelper;


    private LocationManager locationManager;
    private String provider;
    private static GoogleMap mMap;
    private Marker marker;


      final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    public String query=" SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline," +
            " NewsTable.lat as lat ,NewsTable.lon as lon,NewsTable.place, NewsTable.maintext \n" +
            "            FROM  NewsTable    "
            + "where NewsTable.category=? or NewsTable.category=?" +
            " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";
    final int LOCATION_REQUEST = 1340;
    public Marker lastOpenned = null;

      public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        View rootView = inflater.inflate(R.layout.text_bubble, container, false);
        mMap = MainFragment.mMap;
       mMap.setMyLocationEnabled(true);
      mMap.clear();
          CameraUpdateFactory.zoomTo(8);


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
      MainFragment.mClusterManager.setRenderer(new MyclusterRenderer2(getActivity().getApplicationContext(), mMap, MainFragment.mClusterManager));
          MainFragment. mClusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new MycustomClusterAdapter(getActivity().getLayoutInflater()));
          mMap.setOnMarkerClickListener(MainFragment.mClusterManager);

          MainFragment. mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
              @Override

              public boolean onClusterClick(final Cluster<MyItem> cluster) {

                  NewsListViewFromMap.list_headline = new ArrayList<>();

                  NewsListViewFromMap.list_text = new ArrayList<>();
                  for (MyItem item : cluster.getItems()) {
                      //    Toast.makeText(getActivity(), (String) Integer.toString(cluster.getSize()),
                      //             Toast.LENGTH_LONG).show();
                      NewsListViewFromMap.list_headline.add(item.getSnippet());
                      NewsListViewFromMap.list_text.add(item.getText());
                  }
                  FragmentManager fm = getFragmentManager();
                  Fragment a = fm.findFragmentByTag("FragmentLoc");
                  FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                  fab.hide();
                  //     fm.beginTransaction().replace(R.id.content_frame, new NewsListViewFromMap(),"list").addToBackStack("list")
                  Fragment fragment = new NewsListViewFromMap();
                  fm.beginTransaction().replace(R.id.content_frame, fragment, "list")
                          .addToBackStack("list")
               //    .hide(LocationFragment.this)
                  .commit();


//                getFragmentManager().executePendingTransactions();


                  // fm.beginTransaction().remove(fm.getFragment()).commit();


                  //        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                cluster.getPosition(), (float) Math.floor(mMap
                  //                             .getCameraPosition().zoom + 1)), 300,
                  //              null);
                  return true;
              }
          });


          if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,   mlocListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 10,  mlocListener);

        } else {
            this.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        }
     //    if ( mlocListener !=null  )  {
     //       locationManager.removeUpdates(mlocListener);
     //       locationManager = null;
    //    }

         return rootView;
    }

    private LocationListener mlocListener = new LocationListener() {
        @Override
        public void onLocationChanged (Location location){
            //     System.out.println(a);
            if (location != null) {
                mMap.setMyLocationEnabled(true);
                mMap.clear();
                MainFragment.mMap.clear();
                MainFragment.mClusterManager.clearItems();
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.removeUpdates(mlocListener);
                    locationManager = null;
                }
              Location loc = new Location("L");
      //          loc.setLatitude(lat);
       //         loc.setLongitude(lng);
                LatLng latLng = new LatLng(lat, lng);
                Circle circle = mMap.addCircle(new CircleOptions().center(latLng).radius(200 * 100).fillColor(0x5500ff00).strokeColor(0x5500ff00));
                setUpDatabase(query, loc);

                //  System.out.println(lat);
               // mMap.clear();

          //       Toast.makeText(getActivity(), "Location " + lat + "," + lng, Toast.LENGTH_SHORT).show();
             //   Marker aa = mMap.addMarker(new MarkerOptions().position(latLng).title("Title").snippet("Snippet")
               //         .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location)));
             //   Collection col =MainFragment.mClusterManager.getClusterMarkerCollection();
          //  Collection col =MainFragment.My/. ;
              //  for(Object i : col){

            //       col.iterator().next().;

               // }

           //    Collection a = MainFragment.mClusterManager.get    MainFragment.mClusterManager.get

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));
              //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));

            //    CameraUpdateFactory.zoomTo(8);

                // Zoom in the Google Map

                //  Toast.makeText(getActivity(), "Location " + lat+","+lng,Toast.LENGTH_SHORT).show();
                //   Toast.makeText(getActivity(), "Location " + coordinate.latitude+","+coordinate.longitude,Toast.LENGTH_SHORT).show();

            } else {
                 Toast.makeText(getActivity(), "Location off", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onProviderDisabled (String provider){
            Toast.makeText(getActivity(), "Please Enabled your " + provider, Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onProviderEnabled (String provider){
            Toast.makeText(getActivity(), "Enabled provider " + provider, Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onStatusChanged (String provider,int status, Bundle extras){
            // TODO Auto-generated method stub
            // new LocationFragment();
        }

    };
    private void setUpDatabase(String qu, Location loc ) {
        try {
            dbHelper = new DatabaseHelper(this.getActivity());

            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery(qu , SideBar.MenuFilter);
            // query(DatabaseHelper.TABLE_NAME,, cols,where,null,null, null, null);
            //     MenuFilter=null;
            dbCursor.moveToFirst();

             while (!dbCursor.isAfterLast()) {
                MyItem m = new MyItem(Double.valueOf(this.dbCursor.getString(3))
                        ,Double.valueOf(this.dbCursor.getString(4)),
                        (this.dbCursor.getString(1)),(this.dbCursor.getString(2)),this.dbCursor.getString(5),this.dbCursor.getString(6));

                MainFragment.mClusterManager.addItem(m);

             //   Location mloc= new Location("M");
         //       mloc.setLongitude(Double.valueOf(m.getLon()));
         //       mloc.setLatitude(Double.valueOf(m.getLat()));
          //      float distance = loc.distanceTo(mloc);
          //      int KM = 200;
          //      if (distance <= (1000*KM)) {
//
          //          MainFragment.mClusterManager.addItem(m);
          //      }
                dbCursor.moveToNext();
            }




        } finally

        {
            if (database != null) {
                dbHelper.close();
            }
        }



       //     for (int i = 0; i < markers.size(); i++) {      }




        }


}

