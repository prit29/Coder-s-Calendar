package com.noobsever.codingcontests;

import androidx.core.view.GravityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.aseem.versatileprogressbar.ProgBar;
import com.noobsever.codingcontests.Adapters.TabAdapter;
import com.noobsever.codingcontests.Notifications.NotifyActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;


import java.util.ArrayList;

import static android.view.View.GONE;


public class MainActivity extends StartActivity {


    TabLayout tabLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    ViewPager viewPager;
    ProgBar progBar;
    int time;
    private InterstitialAd mInterstitialAd;
    TabAdapter adapter;
    SharedPreferences preferences;
    private ShimmerFrameLayout mShimmerViewContainer;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);


        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        progBar = findViewById(R.id.myProgBar);
        progBar.setVisibility(GONE);
        if(!CheckConnection())
        {
            Snackbar snackbar = Snackbar
                    .make(drawer, "No Internet Connection !!!", Snackbar.LENGTH_LONG);
            snackbar.show();
            time=0;
        }
        else
        {
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmer();
            ApiCall();
            time=3000;
        }

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        Constants constants = new Constants();
        preferences = getSharedPreferences(constants.TABS,MODE_PRIVATE);
        SharedPreferences My_pref = getSharedPreferences(constants.LAYOUTTYPE, Context.MODE_PRIVATE);
        if (My_pref.getString(constants.CURRENT, constants.TYPE_1).equals(constants.TYPE_2)) {
            startActivity(new Intent(MainActivity.this, Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
            return;
        }


        arrayList =new ArrayList<>();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
               AddTab();
            }
        }, time);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5901018826330107/8290870054");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                int p= preferences.getInt(c.COUNTER,0);
                p++;
                if(p%3==0)
                {
                    mInterstitialAd.show();
                    p=0;
                }

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(c.COUNTER,p);
                editor.commit();
            }
        });



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwipeRefresh();

            }
        });




    }



    boolean CheckConnection()
    {
        ConnectivityManager cm =
                (ConnectivityManager)getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if(isConnected) {return  true;}
        else {return false;}
    }


    void AddTab()
    {

        mShimmerViewContainer.setVisibility(View.GONE);
        mShimmerViewContainer.stopShimmer();
        arrayList.clear();
        tabLayout.removeAllTabs();

        if(preferences.getInt(constants.TABLE_CODEFORCES,1)==1)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Codeforces"));
            arrayList.add(constants.TABLE_CODEFORCES);
        }
        if(preferences.getInt(constants.TABLE_CODECHEF,1)==1)
        {
            tabLayout.addTab(tabLayout.newTab().setText("CodeChef"));
            arrayList.add(constants.TABLE_CODECHEF);
        }
        if(preferences.getInt(constants.TABLE_HACKERRANK,1)==1)
        {
            tabLayout.addTab(tabLayout.newTab().setText("HakerRank"));
            arrayList.add(constants.TABLE_HACKERRANK);
        }
        if(preferences.getInt(constants.TABLE_HACKEREARTH,1)==1)
        {
            tabLayout.addTab(tabLayout.newTab().setText("HackerEarth"));
            arrayList.add(constants.TABLE_HACKEREARTH);
        }
        if(preferences.getInt(constants.TABLE_TOPCODER,1)==1)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Topcoder"));
            arrayList.add(constants.TABLE_TOPCODER);
        }
        if(preferences.getInt(constants.TABLE_SPOJ,1)==1)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Spoj"));
            arrayList.add(constants.TABLE_SPOJ);
        }
        if(preferences.getInt(constants.TABLE_ATCODER,1)==1)
        {
            tabLayout.addTab(tabLayout.newTab().setText("Atcoder"));
            arrayList.add(constants.TABLE_ATCODER);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        adapter = new TabAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount(),arrayList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Toast.makeText(MainActivity.this,String.valueOf(tab.getPosition()),Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        drawer.closeDrawer(GravityCompat.START);

        switch (item.getItemId())
        {

            case R.id.nav_changelayout:
            {
                SharedPreferences rr = getSharedPreferences(constants.LAYOUTTYPE,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = rr.edit();
                editor1.putString(constants.CURRENT,constants.TYPE_2);
                editor1.commit();
                startActivity(new Intent(MainActivity.this,Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
            case R.id.nav_reset:
            {
                Refresh();
                return true;

            }
            case R.id.nav_settings:
            {
                startActivity(new Intent(MainActivity.this,Settings_activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
            }


            case R.id.nav_notifications:
            {
                startActivity(new Intent(MainActivity.this, NotifyActivity.class));
                return true;
            }
            case R.id.nav_devs:
            {
                startActivity(new Intent(MainActivity.this, DevelopersActivity.class));
                return true;
            }

            case R.id.nav_suggest:
            {
                startActivity(new Intent(MainActivity.this, SuggestActivity.class));
                return true;
            }
            case R.id.nav_signout:
            {
                SignOut();
                return true;
            }
            case R.id.nav_help:
            {
                    OpenHelp();
                    return true;
            }
            case R.id.nav_share:
            {
                Share();
                return true;
            }
            case R.id.nav_rate:
            {

                Rate();
                return true;
            }


        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dots,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.interchange:
            {
                SharedPreferences rr = getSharedPreferences(constants.LAYOUTTYPE,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = rr.edit();
                editor1.putString(constants.CURRENT,constants.TYPE_2);
                editor1.commit();
                startActivity(new Intent(MainActivity.this,Main2Activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
            case R.id.refresh:
            {
                Refresh();
                return true;
            }
            case R.id.settings:
            {
                startActivity(new Intent(MainActivity.this,Settings_activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
            }
            case R.id.about:
            {
                startActivity(new Intent(MainActivity.this, DevelopersActivity.class));
                return true;
            }

            case R.id.logout:
            {
                SignOut();
                return true;
            }

        }
        return false;
    }

    @Override
    protected void onResume() {
        mShimmerViewContainer.startShimmer();
        super.onResume();
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {


        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar = Snackbar
                .make(drawer, "Press BACK again to exit", Snackbar.LENGTH_SHORT);
        snackbar.show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    void Refresh()
    {
        final int i= viewPager.getCurrentItem();
        if(CheckConnection()) {
            progBar.setVisibility(View.VISIBLE);
            ApiCall();
            ApiCall2();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(i);
                    progBar.setVisibility(View.GONE);

                }
            }, 2000);
        }
        else
        {
            Snackbar snackbar = Snackbar
                    .make(drawer, "No Internet Connection !!!", Snackbar.LENGTH_LONG);
            snackbar.show();

        }

    }

    void SwipeRefresh()
    {
        final int i= viewPager.getCurrentItem();
        if(CheckConnection()) {
            ApiCall();
            ApiCall2();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setAdapter(adapter);
                    viewPager.setCurrentItem(i);
                    swipeRefreshLayout.setRefreshing(false);

                }
            }, 2000);
        }
        else
        {
            Snackbar snackbar = Snackbar
                    .make(drawer, "No Internet Connection !!!", Snackbar.LENGTH_LONG);
            snackbar.show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }



}
