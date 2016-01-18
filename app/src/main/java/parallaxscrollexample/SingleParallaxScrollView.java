package parallaxscrollexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s1908114.newsapp.R;

import NewsApp.fragment.MapArticleActivity;

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
         TextView t = (TextView) findViewById(R.id.article_title);
        TextView t2 = (TextView) findViewById(R.id.article_text);
        TextView t3 = (TextView) findViewById(R.id.article_date);
        TextView t4 = (TextView) findViewById(R.id.article_place);
       t.setText(headline);
        t2.setText(text);
        t3.setText(date);
        t4.setText(place);

        ImageButton gotomap =  (ImageButton) findViewById(R.id.imageButtonGoToMap);
        gotomap.setOnClickListener(startListener);



	}

    private View.OnClickListener startListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent1 = new Intent(getApplicationContext(), MapArticleActivity.class);

            Intent intent = getIntent();
            intent1.putExtra("lat", intent.getStringExtra("lat"));
            intent1.putExtra("lon", intent.getStringExtra("lon"));
        String lon = intent.getStringExtra("lon");
        //Toast.makeText( getApplicationContext(), lat, Toast.LENGTH_SHORT).show();
            startActivity(intent1);



        }
    };
}