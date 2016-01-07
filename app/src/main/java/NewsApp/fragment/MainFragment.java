package NewsApp.fragment;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.MyMarkerObj;
import com.example.s1908114.newsapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;
import com.google.maps.android.kml.KmlPolygon;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by s1908114 on 27.11.2015.
 */

public class MainFragment extends MapFragment implements OnMapReadyCallback {

    private ClusterManager<MyItem> mClusterManager;
    SQLiteDatabase database ;
    Cursor dbCursor;
    DatabaseHelper dbHelper;
    private CameraPosition cp;
    public  static  GoogleMap mMap;
  //  private   float zoom;
    public String query;
    public static String[] MenuFilter;
    public static String[] DefaultFilter;

    @Override
    public void onResume() {
        super.onResume();
        if (cp != null) {
      //      mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
            //  cp = null;
        }
        setUpMapIfNeeded();

    }

    public void onPause() {

        super.onPause();

//        cp = mMap.getCameraPosition();
          mMap = null;
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
        DefaultFilter = new String[]{"Politics", "Business", "Sport", "Science and Technology", "National"};

        if (MenuFilter == null) {
            MenuFilter =  DefaultFilter;
        } else {

            Bundle mBundle = new Bundle();
            mBundle = getArguments();
            MenuFilter = mBundle.getStringArray("key");
        }

    }


    private void setUpClusterer() {
          mClusterManager = new ClusterManager<MyItem>( getActivity().getApplicationContext(), getMap());

        mClusterManager.setRenderer(new MyClusterRenderer(getActivity().getApplicationContext(), getMap(), mClusterManager));

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {

                Toast.makeText(getActivity(), (String)item.getSnippet(),
                        Toast.LENGTH_LONG).show();
            return false;
        }
    });
        mClusterManager  .setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(final Cluster<MyItem> cluster) {
                Toast.makeText(getActivity(), (String)Integer.toString(cluster.getSize()),
                        Toast.LENGTH_LONG).show();
        //        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                                cluster.getPosition(), (float) Math.floor(mMap
           //                             .getCameraPosition().zoom + 1)), 300,
          //              null);
                return true;
            }
        });
    setUpDatabase();

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
            query = " SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline, CapitalsTable.lat as lat ,CapitalsTable.lon as lon \n" +
                    "            FROM  NewsTable   JOIN CapitalsTable\n" +
                    "            ON NewsTable.statecode=CapitalsTable.code   "
                    + "where NewsTable.category=? or NewsTable.category=?" +
                    " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";
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
                 mClusterManager.addItem(offsetItem);
                // mMap.addMarker(new MarkerOptions() .title(markers.get(i).getTitle())  .snippet(markers.get(i).getSnippet())
                //              .position(latlon)
                //    );
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

}


//public class MainFragment extends Fragment {


//   @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//   View rootView = inflater.inflate(R.layout.fragment_home,container,false);
//       return rootView;
//   }
//}



