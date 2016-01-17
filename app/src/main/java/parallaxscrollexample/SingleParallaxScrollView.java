package parallaxscrollexample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.s1908114.newsapp.R;

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
	}
	


}
