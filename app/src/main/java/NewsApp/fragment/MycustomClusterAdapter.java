package NewsApp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.view.ClusterRenderer;

import java.util.Objects;

/**
 * Created by Amir on 09.01.2016.
 */
public class MycustomClusterAdapter implements GoogleMap.InfoWindowAdapter {
    private final  View view;


    MycustomClusterAdapter(LayoutInflater inflater) {

        view = inflater.inflate(R.layout.popup, null);

     }
    @Override
    public View getInfoWindow(Marker marker) {

        TextView tv = (TextView) view.findViewById(R.id.title);
         tv.setText(marker.getTitle());
        TextView tv2 = (TextView) view.findViewById(R.id.snippet);

          if (MainFragment.clickedcluster != null) {
            tv2.setText(String
                    .valueOf(MainFragment.clickedcluster.getItems().size())
                    + " more offers available");
        }
marker.showInfoWindow();
        return view;
    }



    @Override
    public View getInfoContents(Marker marker) {

return  null;
    }

}






      /*  LayoutInflater inflater=null;

    MycustomClusterAdapter(LayoutInflater inflater) {
            this.inflater=inflater;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return(null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            if (MainFragment.clickedcluster != null) {

            }
                View popup = inflater.inflate(R.layout.popup, null);

                TextView tv = (TextView) popup.findViewById(R.id.title);

                tv.setText(marker.getTitle());
                tv = (TextView) popup.findViewById(R.id.snippet);
                tv.setText(marker.getSnippet());

                return (popup);
            }

}
*/