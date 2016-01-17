package NewsApp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s1908114.newsapp.R;

import java.util.List;

//this class is adapting the news list item view to a custom layout making it possible to view image,title, category, main text, etc

public class CustomListItemViewAdapter extends ArrayAdapter  {
   private final  List<List<String>> result;

    public CustomListItemViewAdapter(Activity context, List result) {
        super(context, R.layout.news_list_view_item,result);
        this.result = result;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater dbinflater = LayoutInflater.from(getContext());

        View customView = dbinflater.inflate(R.layout.news_list_view_item, parent, false);
        List<String> news = this.result.get(position);
        TextView headline = (TextView) customView.findViewById(R.id.heading);
        headline.setText(news.get(0));
        TextView category = (TextView) customView.findViewById(R.id.category);
        category.setText(news.get(2));
        TextView maintext = (TextView) customView.findViewById(R.id.maintext);
        maintext.setText(news.get(3));
        TextView color = (TextView) customView.findViewById(R.id.color);
        switch (news.get(4)){
            case "Politics": color.setBackgroundColor(Color.parseColor("#99ffd500"));break;
            case "Business": color.setBackgroundColor(Color.parseColor("#9900b55b"));break;
            case "Sports": color.setBackgroundColor( Color.parseColor("#99b500a6"));break;
            case "Science and Technology": color.setBackgroundColor(Color.parseColor("#991b00b5"));break;
            case "Education": color.setBackgroundColor(Color.parseColor("#990079b5"));break;
        }
        return customView;
    }
}
