package com.noobsever.codingcontests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Settings_activity extends AppCompatActivity {

    SharedPreferences preferences;
    Constants c;
    AdView mAdView;
    CheckBox cf,cc,hr,he,tc,sp,ac,format;
    RadioButton r0,r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);

        mAdView = (AdView) findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-5901018826330107/2630994045");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        c= new Constants();
        preferences = getSharedPreferences(c.TABS,MODE_PRIVATE);

        format = findViewById(R.id.hourformat);
        cf = findViewById(R.id.check_codeforces);
        cc = findViewById(R.id.check_codechef);
        hr = findViewById(R.id.check_hackerrank);
        he = findViewById(R.id.check_hackerearth);
        tc = findViewById(R.id.check_topcoder);
        sp = findViewById(R.id.check_spoj);
        ac = findViewById(R.id.check_atcoder);
        r0= findViewById(R.id.radio0);
        r1= findViewById(R.id.radio1);
        r2= findViewById(R.id.radio2);

        if(preferences.getInt(c.OFFSET,15)==7)
        {
            r0.setChecked(true);
        }
        else if (preferences.getInt(c.OFFSET,15)==15)
        {
            r1.setChecked(true);
        }
        else
        {
            r2.setChecked(true);
        }

        if(preferences.getInt(c.FORMAT,1)==1)
        {
            format.setChecked(true);
        }
        else
        {
            format.setChecked(false);
        }

        if(preferences.getInt(c.TABLE_CODEFORCES,1)==1)
        {
            cf.setChecked(true);
        }
        else {
            cf.setChecked(false);
        }


        if(preferences.getInt(c.TABLE_CODECHEF,1)==1)
        {
            cc.setChecked(true);
        }
        else {
            cc.setChecked(false);
        }



        if(preferences.getInt(c.TABLE_HACKERRANK,1)==1)
        {
            hr.setChecked(true);
        }
        else {
            hr.setChecked(false);
        }


        if(preferences.getInt(c.TABLE_HACKEREARTH,1)==1)
        {
            he.setChecked(true);
        }
        else {
            he.setChecked(false);
        }


        if(preferences.getInt(c.TABLE_TOPCODER,1)==1)
        {
            tc.setChecked(true);
        }
        else {
            tc.setChecked(false);
        }


        if(preferences.getInt(c.TABLE_SPOJ,1)==1)
        {
            sp.setChecked(true);
        }
        else {
            sp.setChecked(false);
        }


        if(preferences.getInt(c.TABLE_ATCODER,1)==1)
        {
            ac.setChecked(true);
        }
        else {
            ac.setChecked(false);
        }

    }

    @Override
    public void onBackPressed() {
            startActivity(new Intent(Settings_activity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
    }


    public void onRadioButtonClicked(View view)
    {
            boolean selected = ((RadioButton) view).isSelected();
            SharedPreferences.Editor editor= preferences.edit();

            switch (view.getId())
            {

                case R.id.radio0:
                {

                    editor.putInt(c.OFFSET,7);
                    break;
                }
                case R.id.radio1:
                {

                    editor.putInt(c.OFFSET,15);
                    break;
                }
                case R.id.radio2:
                {

                    editor.putInt(c.OFFSET,30);
                    break;
                }
            }
            editor.commit();

    }


    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        SharedPreferences.Editor editor= preferences.edit();

        switch(view.getId()) {

            case R.id.hourformat: {
                if (checked) {
                    editor.putInt(c.FORMAT, 1);
                } else {
                    editor.putInt(c.FORMAT, 0);
                }
                break;
            }


            case R.id.check_codeforces: {
                if (checked) {
                    editor.putInt(c.TABLE_CODEFORCES, 1);
                } else {
                    editor.putInt(c.TABLE_CODEFORCES, 0);
                }
                break;
            }

            case R.id.check_codechef:
                if (checked)
                {
                    editor.putInt(c.TABLE_CODECHEF, 1);
                }
                else
                {
                    editor.putInt(c.TABLE_CODECHEF, 0);
                }
                break;

            case R.id.check_hackerrank:
                if (checked)
                {
                    editor.putInt(c.TABLE_HACKERRANK, 1);
                }
                else
                {
                    editor.putInt(c.TABLE_HACKERRANK, 0);
                }
                break;

            case R.id.check_hackerearth:
                if (checked)
                {
                    editor.putInt(c.TABLE_HACKEREARTH, 1);
                }
                else
                {
                    editor.putInt(c.TABLE_HACKEREARTH, 0);
                }
                break;

            case R.id.check_topcoder:
                if (checked)
                {
                    editor.putInt(c.TABLE_TOPCODER, 1);
                }
                else
                {
                    editor.putInt(c.TABLE_TOPCODER, 0);
                }
                break;

            case R.id.check_spoj:
                if (checked)
                {
                    editor.putInt(c.TABLE_SPOJ, 1);
                }
                else
                {
                    editor.putInt(c.TABLE_SPOJ, 0);
                }
                break;

            case R.id.check_atcoder:
                if (checked)
                {
                    editor.putInt(c.TABLE_ATCODER, 1);
                }
                else
                {
                    editor.putInt(c.TABLE_ATCODER, 0);
                }
                break;

        }

        editor.commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
