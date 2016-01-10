package com.example.s1908114.newsapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewsListView extends Fragment {
    SQLiteDatabase database = null;

    Cursor dbCursor;
    DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_news_list_view,container,false);
        ListView listv = (ListView) rootView.findViewById(R.id.lisst);
         queryDataFromDatabase(listv);
        return rootView;

    }




    public void queryDataFromDatabase(ListView  listv) {


        List<String> list_values = new ArrayList<String>();
        try {
            database = dbHelper.getDataBase();

            dbCursor = database.rawQuery("SELECT headline FROM NewsTable;",
                    null);
            dbCursor.moveToFirst();
            int index = dbCursor.getColumnIndex("headline");
            while (!dbCursor.isAfterLast()) {
                String record = dbCursor.getString(index);
                list_values.add(record);
                dbCursor.moveToNext();
            }
        } finally {
            if (database != null) {
                dbHelper.close();
            }
        }
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
                R.layout.news_list_view_item, list_values);

        listv.setAdapter(adapter);
    }
    public void onClickAddDataRecord(View view) {
    }
    public void onClickUpdateList(View view) {
    }
}
