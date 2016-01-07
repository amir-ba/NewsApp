package NewsApp.fragment;

import android.content.Context;

import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Amir on 05.01.2016.
 */

class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {

    public MyClusterRenderer(Context context, GoogleMap map,
                             ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));

        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.title(item.getTitle());
        markerOptions.snippet(item.getSnippet());

    }

    @Override
    protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        //here you have access to the marker itself
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster) {

        //start clustering if at least 2 items overlap cluster.getSize() > 1 && MainFragment.zoom <5
        return  true ;
    }

};
