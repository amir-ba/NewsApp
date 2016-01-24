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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.NewsListView;
import com.example.s1908114.newsapp.R;
import com.example.s1908114.newsapp.SideBar;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by s1908114 on 27.11.2015.
 */
public class HomeFragment extends Fragment {
    SQLiteDatabase database;
    Cursor dbCursor;
    DatabaseHelper dbHelper;

    private LocationManager locationManager;
    private static int KM = 50;
    public static Location LastKnownLoc;
    final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
    public String query = " SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline," +
            " NewsTable.lat as lat ,NewsTable.lon as lon,NewsTable.place as place , NewsTable.maintext as maintext , NewsTable.dates as dates, " +
            "category , image \n" +
            "            FROM  NewsTable    "
            + "where NewsTable.category=? or NewsTable.category=?" +
            " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";
    final int LOCATION_REQUEST = 1340;
    public static boolean locationOn = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.activity_news_list_view, container, false);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, mlocListener);

        } else {
            this.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        }
        LastKnownLoc = getLastKnownLocation();
        //   Toast.makeText(getActivity(), "News in your 200 KM vicinity  ", Toast.LENGTH_SHORT).show();
        return rootView;
    }


    private LocationListener mlocListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //     System.out.println(a);

            if (location != null) {
                locationManager.removeUpdates(mlocListener);
                locationManager = null;
                setUpDatabase(query, location, KM);
                LastKnownLoc = location;
            } else {
                Toast.makeText(getActivity(), "Location off", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(getActivity(), "Please Enabled your " + provider, Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getActivity(), "Enabled provider " + provider, Toast.LENGTH_SHORT).show();

        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
            // new LocationFragment();
        }

    };

    private void setUpDatabase(String qu, Location loc, int KM) {
        MainFragment.list_values.clear();
        List<List<String>> listview = MainFragment.list_values;
        locationOn = true;
        try {
            dbHelper = new DatabaseHelper(this.getActivity());


            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery(qu, SideBar.MenuFilter);

            dbCursor.moveToFirst();

            while (!dbCursor.isAfterLast()) {

                Location mloc = new Location("M");
                mloc.setLongitude(Double.valueOf(this.dbCursor.getString(4)));
                mloc.setLatitude(Double.valueOf(this.dbCursor.getString(3)));
                float distance = loc.distanceTo(mloc);

                if (distance <= (1000 * KM)) {
                    List<String> news_item = new ArrayList<>();

                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("headline")));
                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("maintext")));
                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("dates")));
                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("place")));
                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("category")));

                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("lat")));
                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("lon")));
                    news_item.add(this.dbCursor.getString(dbCursor.getColumnIndex("image")));
                    listview.add(news_item);
                }
                dbCursor.moveToNext();
            }
            ((SideBar) getActivity()).replacefragment(new NewsListView(), "listL");

        } finally

        {
            if (database != null) {
                dbHelper.close();
            }
        }

    }

    public Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);


            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }
}
