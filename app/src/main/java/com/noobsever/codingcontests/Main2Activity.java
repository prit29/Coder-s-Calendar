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
import android.widget.Toast;

import com.aseem.versatileprogressbar.ProgBar;
import com.noobsever.codingcontests.Adapters.TabAdapter2;
import com.noobsever.codingcontests.Notifications.NotifyActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import static android.view.View.GONE;

public class Main2Activity extends StartActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    ProgBar progBar;
    SwipeRefreshLayout swipeRefreshLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    TabAdapter2 adapter;
    BubbleNavigationConstraintView bubbleNavigationLinearView;
    Constants constants;
    int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main2);

        constants = new Constants();

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        progBar =  findViewById(R.id.myProgBar);
        progBar.setVisibility(GONE);

        CheckConnection();


        if (!CheckConnection()) {
            Snackbar snackbar = Snackbar
                    .make(drawer, "No Internet Connection !!!", Snackbar.LENGTH_LONG);
            snackbar.show();
            time = 0;
        } else {
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mShimmerViewContainer.startShimmer();
            ApiCall();
            ApiCall2();
            time = 3000;
        }

        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        SharedPreferences My_pref = getSharedPreferences(constants.LAYOUTTYPE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = My_pref.edit();
        //Toast.makeText(Main2Activity.this,My_pref.getString(constants.CURRENT,constants.TYPE_2),Toast.LENGTH_LONG).show();
        editor.putString(constants.CURRENT, constants.TYPE_2);
        editor.commit();


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

         bubbleNavigationLinearView = findViewById(R.id.floating_top_bar_navigation);

        bubbleNavigationLinearView.setBadgeValue(0, null);
        bubbleNavigationLinearView.setBadgeValue(1, null); //invisible badge
        bubbleNavigationLinearView.setBadgeValue(2, null);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                AddTab();
            }
        }, time);

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
                (ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected) {return true;}
        else {return false;}
    }

    void AddTab()
    {

        mShimmerViewContainer.setVisibility(GONE);
        mShimmerViewContainer.stopShimmer();


        adapter = new TabAdapter2(this,getSupportFragmentManager(), bubbleNavigationLinearView.getChildCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bubbleNavigationLinearView.setCurrentActiveItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        bubbleNavigationLinearView.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                viewPager.setCurrentItem(position, true);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dots,menu);
        return true;
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
                SharedPreferences.Editor editor = rr.edit();
                editor.putString(constants.CURRENT,constants.TYPE_1);
                editor.commit();
                startActivity(new Intent(Main2Activity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                return true;
            }

            case R.id.nav_settings:
            {
                startActivity(new Intent(Main2Activity.this,Settings_activity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
            }
            case R.id.nav_devs:
            {
                startActivity(new Intent(Main2Activity.this, DevelopersActivity.class));
                return true;
            }

            case R.id.nav_suggest:
            {
                startActivity(new Intent(Main2Activity.this, SuggestActivity.class));
                return true;
            }

            case R.id.nav_help:
            {
                OpenHelp();
                return true;
            }
            case R.id.nav_notifications:
            {
                startActivity(new Intent(Main2Activity.this, NotifyActivity.class));
                return true;
            }

            case R.id.nav_signout:
            {
                SignOut();
                return true;
            }

            case R.id.nav_reset:
            {
                Refresh();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.interchange:
            {
                SharedPreferences rr = getSharedPreferences(constants.LAYOUTTYPE,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = rr.edit();
                editor.putString(constants.CURRENT,constants.TYPE_1);
                editor.commit();
                startActivity(new Intent(Main2Activity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();

            }
            case R.id.refresh:
            {
                Refresh();
                return true;
            }
            case R.id.settings:
            {
                startActivity(new Intent(Main2Activity.this,Settings_activity.class));
                finish();
                return true;
            }
            case R.id.about:
            {
                startActivity(new Intent(Main2Activity.this, DevelopersActivity.class));
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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar = Snackbar
                .make(drawer, "Please click BACK again to exit", Snackbar.LENGTH_SHORT);
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
                    progBar.setVisibility(GONE);

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
