package com.example.s1908114.newsapp;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SealedObject;

import NewsApp.fragment.CustomListItemViewAdapter;
import NewsApp.fragment.HomeFragment;
import NewsApp.fragment.MainFragment;
import parallaxscrollexample.SingleParallaxScrollView;

public class NewsListView extends Fragment {
    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());
    public ListView listv;
    private int KM;
    private static int prog = 50;
    private TextView textView;
    private SeekBar seekbar;
    private List<List<String>> list_values = MainFragment.list_values;
    public String query = "SELECT headline , maintext, dates , place , category, lat, lon , image  FROM NewsTable "
            + "where NewsTable.category=? or NewsTable.category=?" +
            " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?  or NewsTable.category=?;";

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_news_list_view, container, false);
        listv = (ListView) rootView.findViewById(R.id.listView1);
        listv.setEmptyView(rootView.findViewById(R.id.emptyElement));
        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar);
        textView = (TextView) rootView.findViewById(R.id.buffer_text);
        if (HomeFragment.locationOn) {

            SetSeekbar(seekbar);
            seekbar.setProgress(prog);
            textView.setText("News  in Distance of : " + String.valueOf(prog)+ "Km");
            seekbar.setVisibility(rootView.VISIBLE);
            textView.setVisibility(rootView.VISIBLE);

        } else {
            seekbar.setVisibility(rootView.GONE);
            textView.setVisibility(rootView.GONE);


        }
        HomeFragment.locationOn = false;

        ArrayAdapter adap = new CustomListItemViewAdapter(this.getActivity(), list_values);
        listv.setAdapter(adap);
        click();


        return rootView;

    }


    public void queryDataFromDatabase() {
        list_values = new ArrayList<List<String>>();
        try {

            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery(query,
                    SideBar.MenuFilter);
            dbCursor.moveToFirst();
            while (!dbCursor.isAfterLast()) {
                List<String> news_item = new ArrayList<>();
                String headline = dbCursor.getString(dbCursor.getColumnIndex("headline"));
                String maintext = dbCursor.getString(dbCursor.getColumnIndex("maintext"));
                String date = dbCursor.getString(dbCursor.getColumnIndex("dates"));
                String place = dbCursor.getString(dbCursor.getColumnIndex("place"));
                String category = dbCursor.getString(dbCursor.getColumnIndex("category"));
                String lat = dbCursor.getString(dbCursor.getColumnIndex("lat"));
                String lon = dbCursor.getString(dbCursor.getColumnIndex("lon"));
                String image = dbCursor.getString(dbCursor.getColumnIndex("image"));
                news_item.add(headline);
                news_item.add(maintext);
                news_item.add(date);
                news_item.add(place);
                news_item.add(category);
                news_item.add(lat);
                news_item.add(lon);
                news_item.add(image);

                list_values.add(news_item);

                dbCursor.moveToNext();
            }
        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }


    }

    public void click() {
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), SingleParallaxScrollView.class);


                List<String> obj = (List<String>) listv.getAdapter().getItem(position);
                intent.putExtra("headline", obj.get(0));
                intent.putExtra("text", obj.get(1));
                intent.putExtra("date", obj.get(2));
                intent.putExtra("place", obj.get(3));
                intent.putExtra("image", obj.get(7));
                intent.putExtra("category", obj.get(4));

                //     intent.putExtra("lat", obj.get(5));
                //   intent.putExtra("lon", obj.get(6));

                startActivity(intent);
                //       Toast.makeText(getActivity(), String.valueOf(listv3.getItemAtPosition((int) (long) id)), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onClickAddDataRecord(View view) {
    }

    public void onClickUpdateList(View view) {
    }

    public boolean checkcolor(String obj) {

        return true;
    }

    public void SetSeekbar(SeekBar seekBar) {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //      Toast.makeText(getActivity(), "Changing seekbar's progress" + progress, Toast.LENGTH_SHORT).show();
                //     setUpDatabase(query, HomeFragment.LastKnownLoc, KM);

                //   mlocListener.onLocationChanged(LastKnownLoc);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //     Toast.makeText(getActivity(), "Started tracking seekbar"  , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                textView.setText("News  in Distance of : " + progress + "Km");
                KM = progress;

                prog = progress;
                HomeFragment.locationOn = true;
                //     Toast.makeText(getActivity(), "Stopped tracking seekbar" + progress , Toast.LENGTH_SHORT).show();
                setUpDatabase(query, HomeFragment.LastKnownLoc, progress);
                //     mlocListener.onLocationChanged(LastKnownLoc);
            }
        });
    }

    private void setUpDatabase(String qu, Location loc, int KM) {
        MainFragment.list_values.clear();
        List<List<String>> listview = MainFragment.list_values;
        try {
            dbHelper = new DatabaseHelper(this.getActivity());


            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery(qu, SideBar.MenuFilter);

            dbCursor.moveToFirst();

            while (!dbCursor.isAfterLast()) {

                Location mloc = new Location("M");
                mloc.setLongitude(Double.valueOf(this.dbCursor.getString(dbCursor.getColumnIndex("lon"))));
                mloc.setLatitude(Double.valueOf(this.dbCursor.getString(dbCursor.getColumnIndex("lat"))));
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
}

