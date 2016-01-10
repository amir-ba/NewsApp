package NewsApp.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;

import java.io.IOException;

/**
 * Created by s1908114 on 27.11.2015.
 */

public class MainFragment extends MapFragment implements OnMapReadyCallback {
public static Cluster clickedcluster;
public static MyItem clickedClusterItem;
    public static ClusterManager<MyItem> mClusterManager;
    SQLiteDatabase database ;
    public static Cursor dbCursor;
    DatabaseHelper dbHelper;
    private CameraPosition cp;
    public  static  GoogleMap mMap;
  //  private   float zoom;
    public String query=" SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline," +
          " CapitalsTable.lat as lat ,CapitalsTable.lon as lon, NewsTable.place \n" +
          "            FROM  NewsTable   JOIN CapitalsTable\n" +
          "            ON NewsTable.statecode=CapitalsTable.code   "
          + "where NewsTable.category=? or NewsTable.category=?" +
          " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";
    public static String[] MenuFilter;
    public static String[] DefaultFilter;


     //      MenuFilter= new String[] {"Politics","Business","Sport","Science & Technology"};
     @Override
    public void onResume() {
        super.onResume();

       setUpMapIfNeeded();

    }

    public void onPause() {

        super.onPause();

//        cp = mMap.getCameraPosition();
      //    mMap = null;
    }

    private void setUpMapIfNeeded() {

        if (mMap == null) {

            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        dbHelper = new DatabaseHelper(this.getActivity());
        setUpDatabase();

        mMap = googleMap;

        setUpMap();

    }

    public void markerFilter() {
        DefaultFilter = new String[]{"Politics", "Business", "Sport", "Science and Technology", "National"};

        if (MenuFilter == null) {
            MenuFilter =  DefaultFilter;
        } else {

            Bundle mBundle ;
            mBundle = getArguments();
            MenuFilter = mBundle.getStringArray("key");
        }

    }


    public void setUpClusterer() {
          mClusterManager = new ClusterManager< >( getActivity().getApplicationContext(), getMap());

        mClusterManager.setRenderer(new MyClusterRenderer(getActivity().getApplicationContext(), getMap(), mClusterManager));

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new MycustomClusterAdapter(getActivity().getLayoutInflater()));

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                Toast.makeText(getActivity(),  item.getSnippet(),
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });


    /*    mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override

          public boolean onClusterClick(final Cluster<MyItem> cluster) {
                //     Marker ma = mMap.addMarker(new MarkerOptions().position(cluster.getPosition())
                //              .title(String.valueOf(cluster.getSize())).snippet(String.valueOf(cluster.getSize())));
                //      ma.showInfoWindow();
                clickedcluster = cluster;

                //        for (Object item : MainFragment.clickedcluster.getItems()) {
                //         Toast.makeText(getActivity(), (String) Integer.toString(cluster.getSize()),
                //                   Toast.LENGTH_LONG).show();
                //       }


                //        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                cluster.getPosition(), (float) Math.floor(mMap
                //                             .getCameraPosition().zoom + 1)), 300,
                //              null);
                return false;
            }
        });
*/
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                clickedClusterItem = item;
         //       mClusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new MycustomClusterAdapter());


                return false;
            }
        });

    //    mClusterManager.getClusterMarkerCollection().getMarkers().showInfoWindow()

}


    private void setUpquery(String qu  ) {

         try {

            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery(qu , MenuFilter);
            // query(DatabaseHelper.TABLE_NAME,, cols,where,null,null, null, null);
            //     MenuFilter=null;
            dbCursor.moveToFirst();


            while (!dbCursor.isAfterLast()) {
                MyItem m = new MyItem(Double.valueOf(this.dbCursor.getString(3))
                                      ,Double.valueOf(this.dbCursor.getString(4)),
                       (this.dbCursor.getString(1)),(this.dbCursor.getString(2)),this.dbCursor.getString(5));


                mClusterManager.addItem(m);

                dbCursor.moveToNext();
            }

            //     for (int i = 0; i < markers.size(); i++) {      }



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

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(50.669155, 10.746248), 5));
          //  mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().
                    setMapToolbarEnabled(false);
            mMap.getUiSettings().
                    setMyLocationButtonEnabled(false);
            mMap.getUiSettings().
                    setZoomControlsEnabled(false);
           // zoom= mMap.getCameraPosition().zoom;
            setUpClusterer();

            setUpquery(query);


        } catch (Exception e) {
            Log.e("Exception caugh eeet", e.toString());
        }


    }


    private void moveCameraToKml(KmlLayer kmlLayer) {
        //Retrieve the first container in the KML layer
        KmlContainer container = kmlLayer.getContainers().iterator().next();
        //Retrieve a nested container within the first container
        container = container.getContainers().iterator().next();
        //Retrieve the first placemark in the nested container
        KmlPlacemark placemark = container.getPlacemarks().iterator().next();
        //Retrieve a polygon object in a placemark
        KmlPolygon polygon = (KmlPolygon) placemark.getGeometry();
        //Create LatLngBounds of the outer coordinates of the polygon
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : polygon.getOuterBoundaryCoordinates()) {
            builder.include(latLng);
        }
        if (cp == null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(builder.build().getCenter(), 5));
            cp = mMap.getCameraPosition();
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(50.051877, 12.741517), 5));
        }
    }
    private void setUpDatabase(   ) {
        try {
            markerFilter();
            dbHelper.createDataBase();

        } catch (IOException ioe) {
            Log.e("heloH", "heloH");
        }}


}


//public class MainFragment extends Fragment {


//   @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//   View rootView = inflater.inflate(R.layout.fragment_home,container,false);
//       return rootView;
//   }
//}



