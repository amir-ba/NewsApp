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
//CLASS THAT INITIATES THE MAP AFTER IT IS READY
public class MainFragment extends MapFragment implements OnMapReadyCallback {
    public static Cluster clickedcluster;
    public static ClusterManager<MyItem> mClusterManager;
    SQLiteDatabase database;
    public static Cursor dbCursor;
    DatabaseHelper dbHelper;
    public static GoogleMap mMap;
    public static List<List<String>> list_values = new ArrayList<List<String>>();
    public String query = " SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline," +
            " CapitalsTable.lat as lat ,CapitalsTable.lon as lon, NewsTable.place ,NewsTable.maintext , NewsTable.dates , NewsTable.category ,NewsTable.image \n" +
            "            FROM  NewsTable   JOIN CapitalsTable\n" +
            "            ON NewsTable.statecode=CapitalsTable.code   "
            + "where NewsTable.category=? or NewsTable.category=?" +
            " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";


    // when map is resumed run the map creation again
    @Override
    public void onResume() {
        super.onResume();

        setUpMapIfNeeded();
        if (mMap != null) {
            mMap.clear();
            onMapReady(getMap());
        }

    }
    // on start
    public void onStart() {
        super.onStart();

        setUpMapIfNeeded();

    }

    public void onPause() {

        super.onPause();


    }

    // wait for the map t be ready and call it in Asyncrnis way
    private void setUpMapIfNeeded() {

        if (mMap == null) {
            // get the map
            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        dbHelper = new DatabaseHelper(this.getActivity());
        // when map is ready run the creation of the markers and clusters
        setUpDatabase();
        // create google map element
        mMap = googleMap;
        // show the map with contents
        setUpMap();

    }

    //determine the filter on the query if there is no filter   selected
    public void markerFilter() {

        if (SideBar.MenuFilter == null) {
            SideBar.MenuFilter = new String[]{"Politics", "Business", "Sports", "Science and Technology",  "Education"};
        }

    }

    // set the clusters for map
    public void setUpClusterer() {
        //define clustermanager
        mClusterManager = new ClusterManager<>(getActivity().getApplicationContext(), getMap());
        //set a custom renderer for the cluster manager
        mClusterManager.setRenderer(new MyClusterRenderer(getActivity().getApplicationContext(), getMap(), mClusterManager));

        mMap.setOnCameraChangeListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        // when clicking the item clusters we get a list of items
        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override

            public boolean onClusterClick(final Cluster<MyItem> cluster) {
                list_values.clear();
                clickedcluster = cluster;
                // add each item in clusters to the list
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
                    list_values.add(news_item);

                }
                // remove the fab button
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.hide();
                //replace the fragment with the main view
                FragmentManager fm = getFragmentManager();
                Fragment a = fm.findFragmentByTag("FragmentLoc");
                ((SideBar) getActivity()).replacefragment(new NewsListView(), "list");


                return true;
            }
        });


    }

    // run the query and add the elements to cluster
    private void setUpquery(String qu) {

        try {
            // get database
            database = dbHelper.getDataBase();
            dbCursor = database.rawQuery(qu, SideBar.MenuFilter);
            dbCursor.moveToFirst();

            //get items for each cluster on map from db
            while (!dbCursor.isAfterLast()) {
                MyItem m = new MyItem(
                        Double.valueOf(this.dbCursor.getString(3))
                        , Double.valueOf(this.dbCursor.getString(4)),
                        (this.dbCursor.getString(1)),
                        (this.dbCursor.getString(2)),
                        this.dbCursor.getString(5),
                        this.dbCursor.getString(6),
                        this.dbCursor.getString(7),
                        this.dbCursor.getString(8),
                        this.dbCursor.getString(9));

                // add items to database
                mClusterManager.addItem(m);

                dbCursor.moveToNext();
            }

        } finally

        {
            //close database
            if (database != null) {
                dbHelper.close();
            }
        }

    }

    //  creates the map
    private void setUpMap() {
        try {
            // creates the kml reader
            KmlLayer kmlLayer = new KmlLayer(getMap(), R.raw.levv, getActivity().getApplicationContext());
            kmlLayer.addLayerToMap();
            // set up map options
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(50.669155, 10.746248), 5));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().
                    setMapToolbarEnabled(false);
            mMap.getUiSettings().
                    setMyLocationButtonEnabled(false);
            mMap.getUiSettings().
                    setZoomControlsEnabled(false);
            // set up cluster settings
            setUpClusterer();
            // get data from database
            setUpquery(query);


        } catch (Exception e) {
            Log.e("Exception caugh eeet", e.toString());
        }


    }


    // start creating the database
    private void setUpDatabase() {
        try {
            markerFilter();
            dbHelper.createDataBase();

        } catch (IOException ioe) {
            Log.e("heloH", "heloH");
        }
    }


}

 


