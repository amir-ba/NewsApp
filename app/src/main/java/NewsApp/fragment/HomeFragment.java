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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s1908114.newsapp.DatabaseHelper;
import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.NewsListView;
import com.example.s1908114.newsapp.NewsListViewFromMap;
import com.example.s1908114.newsapp.R;
import com.example.s1908114.newsapp.SideBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

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
private String provider;
final String[] LOCATION_PERMS = {Manifest.permission.ACCESS_FINE_LOCATION};
public String query=" SELECT   NewsTable.id, NewsTable.category as category ,NewsTable.headline as headline," +
        " NewsTable.lat as lat ,NewsTable.lon as lon,NewsTable.place, NewsTable.maintext , NewsTable.dates\n" +
        "            FROM  NewsTable    "
        + "where NewsTable.category=? or NewsTable.category=?" +
        " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?";
final int LOCATION_REQUEST = 1340;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

         View rootView = inflater.inflate(R.layout.activity_news_list_view, container, false);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.hide();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,   mlocListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000000, 10, mlocListener);


        } else {
            this.requestPermissions(LOCATION_PERMS, LOCATION_REQUEST);

        }

         Toast.makeText(getActivity(), "News in your 200 KM vicinity  ", Toast.LENGTH_SHORT).show();


        return rootView;
     }


private LocationListener mlocListener = new LocationListener() {
    @Override
    public void onLocationChanged (Location location){
        //     System.out.println(a);
        if (location != null) {
                  locationManager.removeUpdates(mlocListener);
                locationManager = null;
             setUpDatabase(query, location,200);

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
    private void setUpDatabase(String qu, Location loc ,int KM) {
        try {
            dbHelper = new DatabaseHelper(this.getActivity());
            NewsListViewFromMap.list_headline = new ArrayList<>();

            NewsListViewFromMap.list_text = new ArrayList<>();
            NewsListViewFromMap.list_date= new ArrayList<>();
            NewsListViewFromMap.list_place= new ArrayList<>();
            List<Map<String, String>> data = new ArrayList<Map<String, String>> ();
            SimpleAdapter  adapter5 = new SimpleAdapter (this.getActivity(),data,
                    R.layout.news_list_view_item1, new String[] {"title", "place"},
                    new int[] {R.id.listitem1,
                            R.id.listitem2}) ;

            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery(qu , SideBar.MenuFilter);

            dbCursor.moveToFirst();

            while (!dbCursor.isAfterLast()) {

                   Location mloc= new Location("M");
                   mloc.setLongitude(Double.valueOf( this.dbCursor.getString(4)));
                        mloc.setLatitude(Double.valueOf(this.dbCursor.getString(3)));
                      float distance = loc.distanceTo(mloc);

                      if (distance <= (1000*KM)) {
                          NewsListViewFromMap.list_headline.add(this.dbCursor.getString(2));
                          NewsListViewFromMap.list_text.add(this.dbCursor.getString(6));
                          NewsListViewFromMap.list_date.add(this.dbCursor.getString(7));
                          NewsListViewFromMap.list_place.add(this.dbCursor.getString(5));
                      }
                dbCursor.moveToNext();
            }
            ((SideBar)getActivity()).replacefragment(new NewsListViewFromMap(),"listL");

        } finally

        {
            if (database != null) {
                dbHelper.close();
            }
        }

    }

}
