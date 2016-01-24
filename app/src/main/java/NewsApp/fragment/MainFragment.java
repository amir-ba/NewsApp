package NewsApp.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.NewsListView;
import com.example.s1908114.newsapp.R;
import com.example.s1908114.newsapp.SideBar;
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
import java.util.ArrayList;
import java.util.List;

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
    public  static   List<List<String>> list_values  = new ArrayList<List<String>>();
    ;
  //  private   float zoom;
    public String query=" SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline," +
          " CapitalsTable.lat as lat ,CapitalsTable.lon as lon, NewsTable.place ,NewsTable.maintext , NewsTable.dates , NewsTable.category ,NewsTable.image \n" +
          "            FROM  NewsTable   JOIN CapitalsTable\n" +
          "            ON NewsTable.statecode=CapitalsTable.code   "
          + "where NewsTable.category=? or NewsTable.category=?" +
          " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";


     //      MenuFilter= new String[] {"Politics","Business","Sport","Science & Technology"};

    @Override
    public void onResume() {
        super.onResume();

       setUpMapIfNeeded();
        if (mMap != null) {
            mMap.clear();
             onMapReady(getMap());
        }

    }
     public void onStart() {
         super.onStart();

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

        if (SideBar.MenuFilter == null) {
            SideBar.MenuFilter = new String[]{"Politics", "Business", "Sports", "Science and Technology", "National", "Education"};
        }

    }


    public void setUpClusterer() {
          mClusterManager = new ClusterManager< >( getActivity().getApplicationContext(), getMap());

        mClusterManager.setRenderer(new MyClusterRenderer(getActivity().getApplicationContext(), getMap(), mClusterManager));

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
      //  mClusterManager.getClusterMarkerCollection().setOnInfoWindowAdapter(new MycustomClusterAdapter(getActivity().getLayoutInflater()));




       mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
           @Override

           public boolean onClusterClick(final Cluster<MyItem> cluster) {
            list_values.clear();
               clickedcluster = cluster;
               for (MyItem item : cluster.getItems()) {
                   List<String> news_item = new ArrayList<>();

                  news_item.add(item.getSnippet());
                   news_item.add(item.getText());
                   news_item.add(item.getDate());
                   news_item.add(item.getPlace());
                   news_item.add(item.getCategory());
                   news_item.add(String.valueOf(item.getLat()));
                   news_item.add(String.valueOf(item.getLon()));

                   news_item.add(item.getUrl());

                    list_values .add(news_item);

               }
               FragmentManager fm = getFragmentManager();
             Fragment a = fm.findFragmentByTag("FragmentLoc");
               FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
               fab.hide();
                ((SideBar)getActivity()).replacefragment(new NewsListView(),"list");


               return true;
           }
       });



    //    mClusterManager.getClusterMarkerCollection().getMarkers().showInfoWindow()

}


    private void setUpquery(String qu  ) {

         try {

            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery(qu , SideBar.MenuFilter);
            // query(DatabaseHelper.TABLE_NAME,, cols,where,null,null, null, null);
            //     MenuFilter=null;
            dbCursor.moveToFirst();


            while (!dbCursor.isAfterLast()) {
                MyItem m = new MyItem(
                        Double.valueOf(this.dbCursor.getString(3))
                        ,Double.valueOf(this.dbCursor.getString(4)),
                       (this.dbCursor.getString(1)),
                        (this.dbCursor.getString(2)),
                        this.dbCursor.getString(5),
                        this.dbCursor.getString(6),
                        this.dbCursor.getString(7),
                        this.dbCursor.getString(8),
                        this.dbCursor.getString(9) );


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

            KmlLayer kmlLayer = new KmlLayer(getMap(), R.raw.levv, getActivity().getApplicationContext());
            kmlLayer.addLayerToMap();

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



    private void setUpDatabase(   ) {
        try {
            markerFilter();
            dbHelper.createDataBase();

        } catch (IOException ioe) {
            Log.e("heloH", "heloH");
        }}




}

 


