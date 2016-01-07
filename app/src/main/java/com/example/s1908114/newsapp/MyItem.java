package com.example.s1908114.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

/**
 * Created by Amir on 05.01.2016.
 */
public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String title;
    private final String snippet;


    public MyItem(double lat, double lng, String title, String snippet) {
        this.title = title;
        this.snippet = snippet;
        mPosition = new LatLng(lat, lng);


    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle() {
        return title;
    }


    public String getSnippet() {
        return snippet;
    }

    //public void setSnippet(String snippet) {
    //     this.snippet = snippet;
    // }


}
