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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import parallaxscrollexample.SingleParallaxScrollView;

public class NewsListViewFromMap extends Fragment {
   public static List<String> list_headline = new ArrayList<String>();
  public static List<String> list_text = new ArrayList<String>();
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_news_list_view,container,false);
         ListView listv = (ListView) rootView.findViewById(R.id.lisst);
         ListView listv2 = (ListView) rootView.findViewById(R.id.lisst1);
         queryDataFromDatabase(listv,listv2);
        FragmentManager fm = getFragmentManager();

      //  fm.findFragmentByTag("list").getView().setBackgroundColor(Color.WHITE);
         return rootView;

    }




    public void queryDataFromDatabase(final ListView  listv,final ListView  listv2) {

         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_item, list_headline);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_text, list_text);
        listv2.setAdapter(adapter2);

        listv.setAdapter(adapter);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), SingleParallaxScrollView.class);
                intent.putExtra("headline",String.valueOf(listv.getItemAtPosition((int) (long)id)));
                intent.putExtra("text",String.valueOf(listv2.getItemAtPosition((int) (long)id)));

                startActivity(intent);
               // intent.putExtra("text", "SecondKeyValue");
                Toast.makeText(getActivity(), "test", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
