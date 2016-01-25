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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import NewsApp.fragment.CustomListItemViewAdapter;
import NewsApp.fragment.HomeFragment;
import NewsApp.fragment.MainFragment;

// FRAGMENT FOR ALL THE LISTS VIEWS
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
    // initiate
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // create the view to shw
        View rootView = inflater.inflate(R.layout.activity_news_list_view, container, false);
        listv = (ListView) rootView.findViewById(R.id.listView1);
        listv.setEmptyView(rootView.findViewById(R.id.emptyElement));
        seekbar = (SeekBar) rootView.findViewById(R.id.seekBar);
        textView = (TextView) rootView.findViewById(R.id.buffer_text);
        // only show the seekbar in lcation near me fragment and not other lists
        if (HomeFragment.locationOn) {

            SetSeekbar(seekbar);
            seekbar.setProgress(prog);
            textView.setText("News  in Distance of : " + String.valueOf(prog) + "Km");
            seekbar.setVisibility(rootView.VISIBLE);
            textView.setVisibility(rootView.VISIBLE);

        } else {
            seekbar.setVisibility(rootView.GONE);
            textView.setVisibility(rootView.GONE);


        }
        // set a custom list adapter on the elements of List_value
        HomeFragment.locationOn = false;
        ArrayAdapter adap = new CustomListItemViewAdapter(this.getActivity(), list_values);
        listv.setAdapter(adap);
        // set click listner on list items
        click();


        return rootView;

    }

    // access the database and create the list of items in the static list
    public void queryDataFromDatabase() {
        // empty the list
        list_values = new ArrayList<>();
        try {
            // get database
            database = dbHelper.getDataBase();
            dbCursor = database.rawQuery(query,
                    SideBar.MenuFilter);
            dbCursor.moveToFirst();
            // fill the items
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
            // close db
            if (database != null) {
                dbHelper.close();
            }
        }


    }

    // on list item click listener
    public void click() {
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create the article view as intent
                Intent intent = new Intent(getActivity(), SingleParallaxScrollView.class);

                // add the elements data to the intent
                List<String> obj = (List<String>) listv.getAdapter().getItem(position);
                intent.putExtra("headline", obj.get(0));
                intent.putExtra("text", obj.get(1));
                intent.putExtra("date", obj.get(2));
                intent.putExtra("place", obj.get(3));
                intent.putExtra("image", obj.get(7));
                intent.putExtra("category", obj.get(4));
                // start intent
                startActivity(intent);

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

    // set a seekbar listener
    public void SetSeekbar(SeekBar seekBar) {

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            // on change of the position of seekbar
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                // get the seekbar value
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            // end of seekbar change
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // set the text value with the distance selected on seekbar
                textView.setText("News  in Distance of : " + progress + "Km");
                KM = progress;
                prog = progress;
                HomeFragment.locationOn = true;
                // run query with the selected value of the seekbar
                setUpDatabase(query, HomeFragment.LastKnownLoc, progress);
            }
        });
    }

    // sets the database based on the query saves it in a static values when the seekbar is changed
    private void setUpDatabase(String qu, Location loc, int KM) {
        //get the element
        MainFragment.list_values.clear();
        List<List<String>> listview = MainFragment.list_values;
        try {
            //start helper
            dbHelper = new DatabaseHelper(this.getActivity());
            //get the database
            database = dbHelper.getDataBase();
            //run the query
            dbCursor = database.rawQuery(qu, SideBar.MenuFilter);
            //go through elements with cursor
            dbCursor.moveToFirst();
            // on each cursor
            while (!dbCursor.isAfterLast()) {
                //create a the location element
                Location mloc = new Location("M");
                //set the long and lat of the location base on the location provided
                mloc.setLongitude(Double.valueOf(this.dbCursor.getString(dbCursor.getColumnIndex("lon"))));
                mloc.setLatitude(Double.valueOf(this.dbCursor.getString(dbCursor.getColumnIndex("lat"))));
                //calculate the distance of articles to the location
                float distance = loc.distanceTo(mloc);
                // keep the articles that are with the defiend distance from the location provided
                if (distance <= (1000 * KM)) {
                    List<String> news_item = new ArrayList<>();
                    // add the data coming from the data to be transfered to the static listview
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
            // run the fragment to re do the articles
            ((SideBar) getActivity()).replacefragment(new NewsListView(), "listL");

        } finally

        {
            //close db
            if (database != null) {
                dbHelper.close();
            }
        }

    }
}

