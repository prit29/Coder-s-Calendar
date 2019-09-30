package com.noobsever.codingcontests;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SuggestActivity extends AppCompatActivity {


    EditText suggestion;
    Button submit;
    AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest_layout);


        mAdView = (AdView) findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-5901018826330107/1126340689");
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Suggestions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        suggestion = findViewById(R.id.suggest_text);
        submit = findViewById(R.id.suggest_btn);




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(CheckConnection()) {

                    if(suggestion.getText().toString().equals(""))
                    {
                            suggestion.setError("Write Something");
                            suggestion.requestFocus();
                    }
                    else
                    {
                        SendSuggestion(suggestion.getText().toString());
                        suggestion.setText("");
                    }
                }
                else
                {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.r1), "No Internet Connection !!!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }



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

    void  SendSuggestion(String suggestion)
    {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Suggestions");
        final DatabaseReference usersRef = reference.child(user.getUid());

        Date date = new Date();
        String strDateFormat = "dd-MM-yyyy | hh:mm a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);

        final HashMap<String,Object> mapdata = new HashMap<>();
        mapdata.put("Suggestion",suggestion);
        mapdata.put("Time",formattedDate);
        mapdata.put("Email",user.getEmail());


        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersRef.child(String.valueOf(System.currentTimeMillis())).setValue(mapdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(SuggestActivity.this,"Thanks For Your Valuable Suggestion.",Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(SuggestActivity.this,"Please Try Again.",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
