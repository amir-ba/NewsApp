package com.example.s1908114.newsapp;


/**
 * Created by Amir on 04.01.2016.
 */
public class MyMarkerObj   {
    private long id;
    private String title;
    private String snippet;

    private String Lat;
    private String Lon;


    public MyMarkerObj(){

    }

    public MyMarkerObj(    String title ,String snippet,String Lat ,String Lon){

        this.title=title;
        this.snippet=snippet;
        this.Lat= Lat;
        this.Lon=Lon;

    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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





    public String getLat() {
        return Lat;
    }

    public void setLat(String Lat) {
        this.Lat = Lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String Lon) {
        this.Lon = Lon;
    }

}
