package parallaxscrollexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s1908114.newsapp.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class SingleParallaxScrollView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_one_parallax);
        Intent intent = getIntent(); // gets the previously created intent
        String headline = intent.getStringExtra("headline"); // will return "FirstKeyValue"
        String text = intent.getStringExtra("text"); // will return "FirstKeyValue"
        String date = intent.getStringExtra("date"); // will return "FirstKeyValue"
        String place = intent.getStringExtra("place"); // will return "FirstKeyValue"
        String cat = intent.getStringExtra("category"); // will return "FirstKeyValue"
        String url = intent.getStringExtra("image"); // will return "FirstKeyValue"
        TextView t = (TextView) findViewById(R.id.article_title);


        TextView t2 = (TextView) findViewById(R.id.article_text);
        TextView t3 = (TextView) findViewById(R.id.article_date);
        TextView t4 = (TextView) findViewById(R.id.article_place);
        TextView tcat = (TextView) findViewById(R.id.article_cat);
        TextView t6 = (TextView) findViewById(R.id.article_title_back);
        ImageView t5 = (ImageView) findViewById(R.id.article_imageView);
        t.setText(headline);
        Picasso.with(getApplicationContext()).load(url)
                .into(t5);
        t2.setText(text);
        t3.setText(date);
        t4.setText(place);
        tcat.setText(cat);
    }
}