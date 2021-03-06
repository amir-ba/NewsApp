package NewsApp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s1908114.newsapp.R;
 import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

//this class is adapting the news list item view to a custom layout making it possible to view image,title, category, main text, etc

public class CustomListItemViewAdapter extends ArrayAdapter  {
   private final  List<List<String>> result;
    ImageView image;
    public CustomListItemViewAdapter(Activity context, List result) {
        super(context, R.layout.news_list_view_item,result);
        this.result = result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater dbinflater = LayoutInflater.from(getContext());
        //set the list's text and color for the category in list view
        View customView = dbinflater.inflate(R.layout.news_list_view_item, parent, false);
        List<String> news = this.result.get(position);
        TextView headline = (TextView) customView.findViewById(R.id.heading);
        headline.setText(news.get(0));
        TextView category = (TextView) customView.findViewById(R.id.category);
        category.setText(news.get(2));
        TextView maintext = (TextView) customView.findViewById(R.id.maintext);
        maintext.setText(news.get(3));
        // set the article images
          image = (ImageView) customView.findViewById(R.id.imageView);
        Picasso.with(getContext()).load(news.get(7)).resize(80, 80).placeholder(R.mipmap.newspaper_list_icon)
                                  .into(image);
        TextView color = (TextView) customView.findViewById(R.id.color);
        // defining the color for the elements corner in the list- defining categories
        switch (news.get(4)){
            case "Politics": color.setBackgroundColor(Color.parseColor("#8c19be19"));break;
            case "Business": color.setBackgroundColor(Color.parseColor("#8c3b79f4"));break;
            case "Sports": color.setBackgroundColor( Color.parseColor("#8ce5d723"));break;
            case "Science and Technology": color.setBackgroundColor(Color.parseColor("#8cf27d14"));break;
            case "Education": color.setBackgroundColor(Color.parseColor("#8cf422cd"));break;
        }
        return customView;
    }





}
