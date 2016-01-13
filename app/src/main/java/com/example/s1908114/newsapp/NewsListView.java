package com.example.s1908114.newsapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parallaxscrollexample.SingleParallaxScrollView;

public class NewsListView extends Fragment {
    SQLiteDatabase database = null;
    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_news_list_view,container,false);
         ListView listv = (ListView) rootView.findViewById(R.id.lisst);
         ListView listv2 = (ListView) rootView.findViewById(R.id.lisst1);
         ListView listv3 = (ListView) rootView.findViewById(R.id.lisst2);
         ListView listv4 = (ListView) rootView.findViewById(R.id.lisst3);

         queryDataFromDatabase(listv, listv2, listv3, listv4);
         return rootView;

    }




    public void queryDataFromDatabase(final ListView  listv,final ListView  listv2,final ListView  listv3,final ListView  listv4) {
          List<String> list_headline = new ArrayList<String>();
           List<String> list_text = new ArrayList<String>();
       List<String> list_date = new ArrayList<String>();
       List<String> list_place = new ArrayList<String>();
        List<Map<String, String>> data = new ArrayList<Map<String, String>> ();

        try {

            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery("SELECT headline , maintext, dates , place FROM NewsTable "
                    + "where NewsTable.category=? or NewsTable.category=?" +
                    " or NewsTable.category=? or NewsTable.category=? or NewsTable.category=?  or NewsTable.category=?;",
                    SideBar.MenuFilter);
            dbCursor.moveToFirst();
            int index = dbCursor.getColumnIndex("headline");
             while (!dbCursor.isAfterLast()) {
                 List<String> item = new ArrayList<String>();
                 Map<String, String> datum = new HashMap<String, String>(2);
                 String record = dbCursor.getString(index);
                String recordtext = dbCursor.getString(1);
                String recorddate = dbCursor.getString(2);
                String recordplace = dbCursor.getString(3);
                datum.put("title", record);
                    datum.put("place", recordplace);

                  list_headline.add(record);
                 list_text.add(recordtext);
                 list_date.add(recorddate);
                 list_place.add(recordplace);
data.add(datum);
                dbCursor.moveToNext();
            }
        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_item, list_headline);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_text, list_text);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_date, list_date);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_place, list_place);
        SimpleAdapter  adapter5 = new SimpleAdapter (this.getActivity(),data,
                R.layout.news_list_view_item1, new String[] {"title", "place"},
                new int[] {R.id.listitem1,
                        R.id.listitem2}) ;
         listv4.setAdapter(adapter4);
        listv3.setAdapter(adapter3);
        listv2.setAdapter(adapter2);

      // listv.setAdapter(adapter);
        listv.setAdapter(adapter5);
        for(  int i=0; i<   listv.getCount() ; i++){

            HashMap<String, String> obj1 = (HashMap<String, String>) listv.getAdapter().getItem(i);
String ss = obj1.get("place");
             switch(  ss ) {
                case "Munich":
                 //   View dd = listv.getChildAt(i);
                   //     dd.setBackgroundColor(Color.red(4));

            }
       }

         listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 Intent intent = new Intent(getActivity(), SingleParallaxScrollView.class);


                 HashMap<String, String> obj = (HashMap<String, String>) listv.getAdapter().getItem(position);
                  intent.putExtra("headline", obj.get("title"));
                 intent.putExtra("text", String.valueOf(listv2.getItemAtPosition((int) (long) id)));
                 intent.putExtra("date", String.valueOf(listv3.getItemAtPosition((int) (long) id)));
                 intent.putExtra("place", String.valueOf(listv4.getItemAtPosition((int) (long) id)));
                 startActivity(intent);
                 // intent.putExtra("text", "SecondKeyValue");
                 Toast.makeText(getActivity(), String.valueOf(listv3.getItemAtPosition((int) (long) id)), Toast.LENGTH_SHORT).show();

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
