package com.noobsever.codingcontests;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noobsever.codingcontests.ApiCalls.APIClient;
import com.noobsever.codingcontests.ApiCalls.ApiInterface;
import com.noobsever.codingcontests.LogIn.MainLogin;
import com.noobsever.codingcontests.Models.Shedule;
import com.noobsever.codingcontests.Models.SingleObj;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rupins.drawercardbehaviour.CardDrawerLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class StartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    CardDrawerLayout drawer;
    Constants constants;
    int flag=0,flag2=0;
    TextView user_id;
    SharedPreferences preferences,offset;
    ArrayList<String> sites = new ArrayList<>();
    Constants c;
    CardDrawerLayout drawerLayout;
    private static String USERNAME = "Unbeatable";
    private static String APIKEY = "4319ff40b4e6c6279fa723f0cc52e6db6928eb9f";
    private static String START = "start";
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    String date,dateafter;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_start1);

        constants= new Constants();

        offset = getSharedPreferences(c.TABS,MODE_PRIVATE);


        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        date = dateFormat.format(new Date());
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        //Toast.makeText(StartActivity.this,String.valueOf(offset.getInt(constants.OFFSET,7)),Toast.LENGTH_SHORT).show();
        c.add(Calendar.DATE, offset.getInt(constants.OFFSET,15));
        Date currentDatePlusOne = c.getTime();
        dateafter = dateFormat.format(currentDatePlusOne);
       // Toast.makeText(StartActivity.this,dateafter,Toast.LENGTH_SHORT).show();

        db = new DatabaseHandler(StartActivity.this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Contests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.user_id);
        navUsername.setText(user.getEmail());


        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(StartActivity.this);

        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setRadius(Gravity.START, 35);
        drawer.setViewElevation(Gravity.START, 20);

    }


    @Override
    public void setContentView(int layoutResID)
    {
        getLayoutInflater().inflate(layoutResID,(FrameLayout)findViewById(R.id.fragment_container),true);
    }


    public void ApiCall() {


        //Toast.makeText(StartActivity.this,"APi called.",Toast.LENGTH_SHORT).show();


        preferences = getSharedPreferences(constants.TABS,MODE_PRIVATE);

        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        c = new Constants();
        sites.add("codeforces.com");
        sites.add("codechef.com");
        sites.add("hackerrank.com");
        sites.add("hackerearth.com");
        sites.add("topcoder.com");
        sites.add("spoj.pl");
        sites.add("atcoder.jp");
        sites.add(c.TABLE_CODEFORCES);
        sites.add(c.TABLE_CODECHEF);
        sites.add(c.TABLE_HACKERRANK);
        sites.add(c.TABLE_HACKEREARTH);
        sites.add(c.TABLE_TOPCODER);
        sites.add(c.TABLE_SPOJ);
        sites.add(c.TABLE_ATCODER);
        flag=0;


        for (int j = 0; j < 7; j++) {

            final int k = j;

                    Call<Shedule> call = apiService.getShedule(USERNAME, APIKEY, sites.get(j), date, dateafter, START);
                    call.enqueue(new Callback<Shedule>() {
                        @Override
                        public void onResponse(Call<Shedule> call, Response<Shedule> response) {

                            if (response.isSuccessful()) {
                                db.DeleteAll(sites.get(k + 7));
                                if (flag == 0) {
                                    flag = 1;
                                    db.DeleteAll(c.LONG);
                                    db.DeleteAll(c.SHORT);
                                }
                                //Toast.makeText(StartActivity.this, sites.get(k + 5), Toast.LENGTH_LONG).show();
                                List<Shedule.Object> objectList = response.body().getObjects();

                                for (int i = 0; i < objectList.size(); i++) {
                                    //Integer duration, String end, String event, String href, Integer id, SingleObj.Res resource, String start)
                                    Shedule.Object object = objectList.get(i);
                                    //Toast.makeText(StartActivity.this, response.message().toString(), Toast.LENGTH_LONG).show();
                                    SingleObj singleObj = new SingleObj(object.getDuration(), object.getEnd(), object.getEvent(), object.getHref(), object.getId(), new SingleObj.Res(object.getResource().getId(), object.getResource().getName()), object.getStart());
                                    if(preferences.getInt(sites.get(k+7),1)==1)
                                    {
                                        db.addEvent(singleObj, sites.get(k + 7));
                                        if (object.getDuration() <= 14400) {
                                            db.addEvent(singleObj, c.SHORT);
                                        } else {
                                            db.addEvent(singleObj, c.LONG);
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(StartActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Shedule> call, Throwable t) {


                        }

                    });
        }

    }


    public void ApiCall2() {

        ApiInterface apiService = APIClient.getClient().create(ApiInterface.class);
        c = new Constants();
        flag2=0;
        preferences = getSharedPreferences(constants.TABS,MODE_PRIVATE);

        for (int j = 0; j < 7; j++) {
            final int k = j;
            Call<Shedule> call = apiService.getLive(USERNAME, APIKEY,sites.get(j), date, date, "end");
            call.enqueue(new Callback<Shedule>() {
                @Override
                public void onResponse(Call<Shedule> call, Response<Shedule> response) {

                    if (response.isSuccessful()) {
                        if(flag2==0) {
                            flag2=1;
                            db.DeleteAll(c.LIVE);
                        }
                        //Toast.makeText(StartActivity.this, sites.get(k + 5), Toast.LENGTH_LONG).show();
                        List<Shedule.Object> objectList = response.body().getObjects();

                        for (int i = 0; i < objectList.size(); i++) {
                            //Integer duration, String end, String event, String href, Integer id, SingleObj.Res resource, String start)
                            Shedule.Object object = objectList.get(i);
                            //Toast.makeText(StartActivity.this, response.message().toString(), Toast.LENGTH_LONG).show();
                            SingleObj singleObj = new SingleObj(object.getDuration(), object.getEnd(), object.getEvent(), object.getHref(), object.getId(), new SingleObj.Res(object.getResource().getId(), object.getResource().getName()), object.getStart());

                             if(preferences.getInt(sites.get(k+7),1)==1) {
                                db.addEvent(singleObj, c.LIVE);
                            }
                        }

                    } else {
                        Toast.makeText(StartActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<Shedule> call, Throwable t) {


                }

            });
        }
    }
// -------------------------------------------API-CALL END----------------------------------------------------------------------



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dots,menu);
        return true;
    }
*/
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.interchange:
            {

            }
            case R.id.refresh:
            {

            }
            case R.id.settings:
            {

            }
            case R.id.about:
            {

            }

        }
        return false;
    }*/

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.

            drawer.closeDrawer(GravityCompat.START);


            return true;
        }


        @Override
        public void onBackPressed () {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }



    void SignOut()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setMessage("Are You Sure ?")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(StartActivity.this, MainLogin.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    void OpenHelp()
    {
        startActivity(new Intent(StartActivity.this, HelpActivity.class));
    }


    void Share()
    {
        final String appPackageName = getPackageName();
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CODER'S CALENDAR");
            String shareMessage= "\nIf you are a coder then let me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + appPackageName +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void Rate()
    {
        final String appPackageName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }


}


