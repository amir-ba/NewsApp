package NewsApp.fragment;

import android.content.Context;
import android.graphics.Color;

import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.R;
import com.example.s1908114.newsapp.SideBar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by Amir on 05.01.2016.
 */

class MyClusterRenderer extends DefaultClusterRenderer<MyItem> {
private Context cntxt;
    int Hue;
    float Satr;
    float Val;



    public MyClusterRenderer(Context context, GoogleMap map,
                             ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);

        this.cntxt=context;
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));

        super.onBeforeClusterItemRendered(item, markerOptions);

     }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {


    super.onBeforeClusterRendered(cluster, markerOptions);


     }

    @Override
    protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        //here you have access to the marker itself
    }
    @Override
    protected void onClusterRendered(Cluster<MyItem> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);
         //here you have access to the marker itself
    }
    @Override
    protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster) {

        //start clustering if at least 2 items overlap cluster.getSize() > 1 && MainFragment.zoom <5

           return true;
    }
    @Override
     protected int getColor(int clusterSize) {
        String cat =SideBar.category ;

        switch (cat ){
            case "all" : Hue =4;Satr=1;Val=0.6f;break;
            case "politics": Hue = 120;Satr=0.88f;Val=0.74f;break;
            case "business": Hue =218 ;Satr=0.75f;Val=0.95f;break;
            case "social": Hue =310 ;Satr=0.85f;Val=0.95f;break;
            case "science": Hue =28;Satr=0.91f;Val=0.94f ;break;
            case "sports": Hue = 55;;Satr=0.84f;Val=0.89f;

        }

        final float hueRange = Hue;
        final float sizeRange = 300;
        final float size = Math.min(clusterSize, sizeRange);
        final float hue = (sizeRange - size) * (sizeRange - size) / (sizeRange * sizeRange) * hueRange;
        return Color.HSVToColor(new float[]{
               // hue, 1f, .6f
                hue, Satr, Val
        });
    }
}
//yellow 50
//green 150
//blue  200
// G 250
//purple 300
//orangee
//90 150
//200
//300
//