package com.example.s1908114.newsapp;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
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
import NewsApp.fragment.HomeFragment;
import NewsApp.fragment.MainFragment;
import NewsApp.fragment.LocationFragment;

//MAIN ACTIVITY THAT WILL INITIALIZE OOTHER FRAGMENTS
public class SideBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public android.support.v7.app.ActionBar actionbar;
    public static boolean IsTypeList = false;
    public static String[] MenuFilter;
    public Fragment fragment;
    public static String category;

    @Override
    //initialize activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create the content
        setContentView(R.layout.activity_side_bar);
        //get the toolbar and set the action bar content
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();

        // get the floating action button on the screen and set the content and the popup message on click
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New around your area are shown here. select city to see the headlines ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                FragmentManager fm = getFragmentManager();
                MainFragment.mMap.clear();
                MainFragment.mClusterManager.clearItems();
                // replace the  fragment with the map of the city level news map with the location of the user visible
                fm.beginTransaction().add(R.id.content_frame, new LocationFragment(), "FragmentLoc").addToBackStack("FragmentLoc").commit();
            }

        });

        // create the navigation bar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        category = "all";

        // start the initial fragment showing the main state level map
        replacefragment(new MainFragment(), "Fragment1");


    }

    //  a function to replace the fragments content with the new fragment
    public void replacefragment(Fragment fg, String name) {
        FragmentManager fm = getFragmentManager();

        fm.beginTransaction().replace(R.id.content_frame, fg, name).addToBackStack(name)
                .commit();
    }

    // set of actions when the back button is pressed on different fragments
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // remove or show te fab button on click back
        if ((drawer.isDrawerOpen(GravityCompat.START))) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if ((getFragmentManager().getBackStackEntryCount() > 0)) {
            getFragmentManager().popBackStack();
        }
        if ((getFragmentManager().findFragmentByTag("list1") != null) && getFragmentManager().findFragmentByTag("list1").isVisible()) {
            FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
            fab.show();
        }
        if ((getFragmentManager().findFragmentByTag("list") != null) && getFragmentManager().findFragmentByTag("list").isVisible()) {
            FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
            fab.show();


        } else {
            super.onBackPressed();
        }
    }

    // initiate the sidebar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.side_bar, menu);
        return true;
    }

    // create a map fragment or list fragment of the elements based on user selection
    public void CreateFragment() {
        fragment = null;
        MainFragment.mMap = null;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        MainFragment.mClusterManager.clearItems();
        if (!IsTypeList) {
            fragment = new MainFragment();
            fab.show();

        } else {
            NewsListView dd = new NewsListView();

            fragment = dd;
            dd.queryDataFromDatabase();
            fab.hide();

        }
    }


    // handle actionbar item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            // if map view is selected it will show the map and the fab button
            case R.id.action_mapview:

                MainFragment.mMap = null;
                MainFragment.mClusterManager.clearItems();
                MainFragment fragment = new MainFragment();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.show();
                IsTypeList = false;
                // replace fragment with map fragment
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, fragment, "Fragment1").addToBackStack("Fragment1").commit();
                return true;
        }
        switch (id) {

            // the about section is shown on selection of about
            case R.id.action_about:
                // set title n actionbar
                if (actionbar.getTitle() == "About") {
                    actionbar.setTitle("All News");
                }
                FragmentManager fm = getFragmentManager();
                actionbar.setTitle("About");
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.hide();
                // replace fragment with about fragment
                fm.beginTransaction().replace(R.id.content_frame, new AboutActivity(), "FragmentAbout").addToBackStack("FragmentAbout").commit();

                return true;


        }
        switch (id) {
            // create a list view on select
            case R.id.action_listviewview:
                IsTypeList = true;
                // change the title on actionbar
                if (actionbar.getTitle() == "About") {
                    actionbar.setTitle("All News");

                }
                // create the list view fragment
                CreateFragment();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.hide();
                replacefragment(fragment, null);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    // navigation view items
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // change the actionbar text and the query values and selection of colors
        FragmentManager fn = getFragmentManager();

        int id = item.getItemId();
        // cases of selecting different values from the navigation viewer
        if (id == R.id.nav_Home) {
            // set the filter list of categories for the query
            MenuFilter = new String[]{"Politics", "Business", "Sports", "Science and Technology", "Education"};
            category = "all";
            //create the fragment based on selection
            CreateFragment();
            //replace with the current fragment
            fn.beginTransaction().replace(R.id.content_frame, fragment, "FragmentHome").commit();
            actionbar.setTitle("All News");
        } else if (id == R.id.nav_Near) {
            fn.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
            actionbar.setTitle("News Near Me");

        } else if (id == R.id.nav_Politics) {
            category = "politics";
            MenuFilter = new String[]{"Politics"};
            CreateFragment();

            fn.beginTransaction().replace(R.id.content_frame, fragment, "FragmentPolitc").commit();
            actionbar.setTitle("Politics");
        } else if (id == R.id.nav_Business) {

            MenuFilter = new String[]{"Business"};
            category = "business";
            CreateFragment();

            fn.beginTransaction().replace(R.id.content_frame, fragment, "FragmentBusiness").commit();
            actionbar.setTitle("Business");
        } else if (id == R.id.nav_SocCul) {
            MenuFilter = new String[]{"Education"};
            category = "social";
            CreateFragment();

            fn.beginTransaction().replace(R.id.content_frame, fragment, "FragmentNational").commit();
            actionbar.setTitle("Social & Cultural");
        } else if (id == R.id.nav_SciTech) {
            MenuFilter = new String[]{"Science and Technology"};
            category = "science";
            CreateFragment();

            fn.beginTransaction().replace(R.id.content_frame, fragment, "Fragmentscience").commit();
            actionbar.setTitle("Science & Technology");


        } else if (id == R.id.nav_Sport) {
            MenuFilter = new String[]{"Sports"};
            category = "sports";
            CreateFragment();

            fn.beginTransaction().replace(R.id.content_frame, fragment, "Fragmentsports").commit();
            actionbar.setTitle("Sports");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
