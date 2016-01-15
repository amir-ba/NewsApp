package com.example.s1908114.newsapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import parallaxscrollexample.SingleParallaxScrollView;

public class NewsListViewFromMap extends Fragment {
    public static List<String> list_headline = new ArrayList<String>();
    public static List<String> list_text = new ArrayList<String>();
    public static List<String> list_date = new ArrayList<String>();
    public static List<String> list_place = new ArrayList<String>();
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_news_list_view,container,false);
         ListView listv = (ListView) rootView.findViewById(R.id.lisst);
       //  ListView listv2 = (ListView) rootView.findViewById(R.id.lisst1);
       //  ListView listv3 = (ListView) rootView.findViewById(R.id.lisst2);
         //ListView listv4 = (ListView) rootView.findViewById(R.id.lisst3);
       //  queryDataFromDatabase(listv, listv2,listv3, listv4);
         queryDataFromDatabase(listv);
        FragmentManager fm = getFragmentManager();
listv.setEmptyView(rootView.findViewById(android.R.id.empty));
       // listv.setEmptyView(rootView.findViewById(R.id.list_alert));
      //  fm.findFragmentByTag("list").getView().setBackgroundColor(Color.WHITE);
         return rootView;

    }




    public void queryDataFromDatabase(final ListView  listv) {

         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_item,  list_headline);
      final  ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_text,  list_text);
      final  ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_date,   list_date);
       final  ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_place, list_place);
//if (adapter==null){ listv.setVisibility(View.VISIBLE);}
      //  listv4.setAdapter(adapter4);
       // listv3.setAdapter(adapter3);
        //listv2.setAdapter(adapter2);

        listv.setAdapter(adapter);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), SingleParallaxScrollView.class);
                intent.putExtra("headline", String.valueOf(listv.getItemAtPosition((int) (long) id)));
                intent.putExtra("text", String.valueOf(adapter2.getItem(position)));
                intent.putExtra("date", String.valueOf(adapter3.getItem(position)));
                intent.putExtra("place", String.valueOf(adapter4.getItem(position)));

                startActivity(intent);
                // intent.putExtra("text", "SecondKeyValue");
             //   Toast.makeText(getActivity(), String.valueOf(listv3.getItemAtPosition((int) (long) id)), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
