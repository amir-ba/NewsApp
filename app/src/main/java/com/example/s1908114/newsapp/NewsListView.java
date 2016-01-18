package com.example.s1908114.newsapp;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import NewsApp.fragment.CustomListItemViewAdapter;
import NewsApp.fragment.HomeFragment;
import NewsApp.fragment.MainFragment;
import parallaxscrollexample.SingleParallaxScrollView;

public class NewsListView extends Fragment {
    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());
     public    ListView listv  ;
    private  List<List<String>> list_values = MainFragment.list_values;
    public   String query = "SELECT headline , maintext, dates , place , category  FROM NewsTable "
        + "where NewsTable.category=? or NewsTable.category=?" +
        " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?  or NewsTable.category=?;";
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_news_list_view,container,false);
        listv = (ListView) rootView.findViewById(R.id.listView1);
        listv.setEmptyView(rootView.findViewById(R.id.emptyElement));

     //   if (list_values.isEmpty() ){
       //  queryDataFromDatabase();
       // }
          ArrayAdapter adap = new CustomListItemViewAdapter(this.getActivity(), list_values);
        listv.setAdapter(adap);
        click();
         return rootView;

    }


    public void queryDataFromDatabase() {
        list_values =   new ArrayList<List<String>>();
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
                news_item.add(headline);
                news_item.add(maintext);
                news_item.add(date);
                news_item.add(place);
                news_item.add(category);

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
                startActivity(intent);
                //       Toast.makeText(getActivity(), String.valueOf(listv3.getItemAtPosition((int) (long) id)), Toast.LENGTH_SHORT).show();

            }
        });
    }

     public void onClickAddDataRecord(View view) {
    }
    public void onClickUpdateList(View view) {
    }
    public boolean checkcolor(String obj){

     return true;
    }
}

