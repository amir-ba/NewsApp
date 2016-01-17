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

import java.util.ArrayList;

/**
 * Created by Amir on 05.01.2016.
 */
public class MyItem implements ClusterItem {
    private final  LatLng mPosition ;
    private long id;
    private String title;
    private String snippet;
    private String Text;
    private  String Place;
    private  String Date;
    private  String Category;
    private   double Lat;
    private   double Lon;

     public MyItem(double lat, double lng, String title, String snippet,String place,String text,String date, String category) {
        this.title = title;
        this.snippet = snippet;
        mPosition = new LatLng(lat, lng);
this.Lat = lat;
        this.Lon=lng;
this.Place = place;
this.Text = text;
this.Date = date;
this.Category = category;


    }


    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }


    public double getLat() {
        return Lat;
    }

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double Lon) {
        this.Lon = Lon;
    }

    //public void setSnippet(String snippet) {
    //     this.snippet = snippet;
    // }
    public String getPlace() {
        return Place;
    }
    public void setPlace(String Place) {
        this.Place = Place;
    }
    public String getText() {
        return Text;
    }
    public void setText(String text) {
        this.Text = text;
    }

    public String getDate() {
        return Date;
    }
    public void setDate(String date) {
        this.Text = date;
    }
    public String getCategory() {
        return Category;
    }

}
