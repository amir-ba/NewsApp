package com.example.s1908114.newsapp;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
 import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

import NewsApp.fragment.HomeFragment;
import NewsApp.fragment.MainFragment;
import NewsApp.fragment.LocationFragment;

public class SideBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener   {
    public android.support.v7.app.ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

    actionbar = getSupportActionBar() ;
  //      CalculateActionBar(this.getApplicationContext());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
        @Override
       public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction().replace(R.id.content_frame, new LocationFragment(), "Fragment2").commit();

        }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,new MainFragment(),"Fragment1").commit();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
        //    return true;
        //}
        switch (id){
            case R.id.action_mapview:
            startActivity(new Intent(this,HomeFragment.class));
            return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fn = getFragmentManager();


        int id = item.getItemId();

        if (id == R.id.nav_Home) {
         //   MainFragment.markerFilter(new String[]{"Politics","Business","Sport","Science & Technology","National"});
            MainFragment.mMap= null;

            MainFragment fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray ("key",     new String[]{"Politics","Business","Sport","Science & Technology","National"});
            fragment.setArguments(bundle);
            fn.beginTransaction().replace(R.id.content_frame,fragment).commit();
            actionbar.setTitle("All News");
        }   else if (id == R.id.nav_Near) {
            fn.beginTransaction().replace(R.id.content_frame,new LocationFragment()).commit();
            actionbar.setTitle("News Near Me");

        } else if (id == R.id.nav_Politics) {
  //    fn.findFragmentById(R.id.content_frame).markerFilter(new String[]{"Politics"});
            MainFragment.mMap= null;

            MainFragment fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray("key", new String[]{"Politics"});
            fragment.setArguments(bundle);

           fn.beginTransaction().replace(R.id.content_frame, fragment).commit();
            actionbar.setTitle("Politics");
        } else if (id == R.id.nav_Business) {
            MainFragment.mMap= null;
            MainFragment fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray ("key",     new String[]{"Business"});

            fragment.setArguments(bundle);

            fn.beginTransaction().replace(R.id.content_frame,  fragment).commit();
            actionbar.setTitle("Business");
        } else if (id == R.id.nav_SocCul) {
            MainFragment.mMap= null;

            MainFragment fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray ("key",     new String[]{"National"});
            fragment.setArguments(bundle);

            fn.beginTransaction().replace(R.id.content_frame,  fragment).commit();
            actionbar.setTitle("Social & Cultural");
        } else if (id == R.id.nav_SciTech) {
            MainFragment.mMap= null;

            MainFragment fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray("key", new String[]{"Science & Technology"});
            fragment.setArguments(bundle);

            fn.beginTransaction().replace(R.id.content_frame,  fragment).commit();
            actionbar.setTitle("Science and Technology");


        } else if (id == R.id.nav_Sport) {
            MainFragment.mMap= null;

            MainFragment fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArray ("key",     new String[]{"Sport"});
            fragment.setArguments(bundle);

            fn.beginTransaction().replace(R.id.content_frame,  fragment).commit();
            actionbar.setTitle("Sports");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
