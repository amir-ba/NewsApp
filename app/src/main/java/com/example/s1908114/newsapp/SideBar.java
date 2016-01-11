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
import android.support.v4.view.ViewPager;
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
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import NewsApp.fragment.HomeFragment;
import NewsApp.fragment.MainFragment;
import NewsApp.fragment.LocationFragment;

public class SideBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener   {
    public android.support.v7.app.ActionBar actionbar;
    public static boolean IsTypeList = false;
    public static String[] MenuFilter;
public  Fragment fragment;

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
                Snackbar.make(view, "New around your area are shown here. select city to see the headlines ", Snackbar.LENGTH_LONG)
                       .setAction("Action", null).show();
            FragmentManager fm = getFragmentManager();
            MainFragment.mMap.clear();
            MainFragment.mClusterManager.clearItems();
        /*


            java.util.Collection<Marker> userCollection =  MainFragment.mClusterManager.getMarkerCollection().getMarkers();
            ArrayList<Marker> userList = new ArrayList<Marker>(userCollection);
            // now is userList empty
            for(Marker marker: userList){
                marker.remove();
            }

            java.util.Collection<Marker> userCollection2 =  MainFragment.mClusterManager.getClusterMarkerCollection() .getMarkers();
            ArrayList<Marker> userList2 = new ArrayList<Marker>(userCollection2);
            // now is userList2 empty
            for(Marker marker: userList2){
                marker.remove();
            }
  */
           // fm.beginTransaction().remove(fragment);
             //fm.popBackStack(null,f)

            fm.beginTransaction().add(R.id.content_frame, new LocationFragment(), "FragmentLoc") .addToBackStack("FragmentLoc") .commit();
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
        fm.beginTransaction().replace(R.id.content_frame, new MainFragment(),"Fragment1") .commit();


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment a =getFragmentManager().findFragmentByTag("list");
        FragmentManager fmm =       getFragmentManager();
        if ((drawer.isDrawerOpen(GravityCompat.START))  ) {
            drawer.closeDrawer(GravityCompat.START);
        } if (  (getFragmentManager().getBackStackEntryCount() > 0)){
         getFragmentManager().popBackStack();
             int ss = (getFragmentManager().getBackStackEntryCount());  }
        if((getFragmentManager().findFragmentByTag("list1") !=null)&& getFragmentManager().findFragmentByTag("list1").isVisible()){
            FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
//int g =   getFragmentManager().getBackStackEntryAt(1).getId();
//String g1 =   getFragmentManager().getBackStackEntryAt(1).getName();
  //          fmm.popBackStack();
    //        FragmentManager fm = getFragmentManager();
      //      Fragment ac = fm.findFragmentByTag("FragmentLoc");
        //    fmm.beginTransaction().show(ac).commit();
          //  System.out.println(g1);           fmm.popBackStack(      null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fab.show();
           // getFragmentManager().popBackStack();
           // getFragmentManager().beginTransaction().show().commit();
        }  if( (getFragmentManager().findFragmentByTag("list") !=null)&&getFragmentManager().findFragmentByTag("list").isVisible()){
            FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
            fab.show();
           // getFragmentManager().popBackStack();

        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_bar, menu);
        return true;
    }
public   void CreateFragment() {
    //   MainFragment.markerFilter(new String[]{"Politics","Business","Sport","Science & Technology","National"});
    fragment= null;
     MainFragment.mMap = null;
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    MainFragment.mClusterManager.clearItems();
    if (!IsTypeList  ) {
          fragment = new  MainFragment();
        fab.show();

    } else {
          fragment = new NewsListView();
        fab.hide();

    }
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
                MainFragment.mMap= null;
                MainFragment.mClusterManager.clearItems();
                MainFragment fragment = new MainFragment();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.show();
                IsTypeList=false;
                  FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame,   fragment, "Fragment1" ).addToBackStack("Fragment1").commit();            return true;


        }
        switch (id){
            case R.id.action_listviewview:
                IsTypeList=true;
                FragmentManager fm = getFragmentManager();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
fab.hide();
                fm.beginTransaction().replace(R.id.content_frame, new NewsListView(),"FragmentList") .commit();

          //      startActivity(new Intent(this,NewsListView.class));
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
            CreateFragment();
            MenuFilter = new String[]{"Politics", "Business", "Sports", "Science and Technology", "National"};

            fn.beginTransaction().replace(R.id.content_frame,fragment,"FragmentHome").commit();
            actionbar.setTitle("All News");
        }   else if (id == R.id.nav_Near) {
            fn.beginTransaction().replace(R.id.content_frame,new LocationFragment()).commit();
            actionbar.setTitle("News Near Me");

        } else if (id == R.id.nav_Politics) {
  //    fn.findFragmentById(R.id.content_frame).markerFilter(new String[]{"Politics"});
      //      MainFragment.mMap= null;
      //      MainFragment.mClusterManager.clearItems();

        //    MainFragment fragment = new MainFragment();
            CreateFragment();

             MenuFilter =new String[]{"Politics"} ;

           fn.beginTransaction().replace(R.id.content_frame, fragment,"FragmentPolitc").commit();
            actionbar.setTitle("Politics");
        } else if (id == R.id.nav_Business) {
            CreateFragment();

            MenuFilter =     new String[]{"Business"} ;


            fn.beginTransaction().replace(R.id.content_frame,  fragment,"FragmentBusiness").commit();
            actionbar.setTitle("Business");
        } else if (id == R.id.nav_SocCul) {
            CreateFragment();
            MenuFilter =    new String[]{"National"} ;

            fn.beginTransaction().replace(R.id.content_frame,  fragment,"FragmentNational").commit();
            actionbar.setTitle("Social & Cultural");
        } else if (id == R.id.nav_SciTech) {
            CreateFragment();
            MenuFilter = new String[]{"Science and Technology"} ;

            fn.beginTransaction().replace(R.id.content_frame,  fragment,"Fragmentscience").commit();
            actionbar.setTitle("Science & Technology");


        } else if (id == R.id.nav_Sport) {
            CreateFragment();
            MenuFilter =     new String[]{"Sports"} ;

            fn.beginTransaction().replace(R.id.content_frame,  fragment,"Fragmentsports").commit();
            actionbar.setTitle("Sports");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
