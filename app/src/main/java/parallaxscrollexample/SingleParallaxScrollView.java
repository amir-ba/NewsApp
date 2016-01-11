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
       // String secondKeyName= v.getStringExtra("secondKeyName"); // will return "SecondKeyValue"
        TextView t = (TextView) findViewById(R.id.article_title);
        TextView t2 = (TextView) findViewById(R.id.article_text);
        t.setText(headline);
        t2.setText(text);
	}
	


	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_github) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/nirhart/ParallaxScroll"));
			startActivity(browserIntent);
		}
		return true;
	}
}
