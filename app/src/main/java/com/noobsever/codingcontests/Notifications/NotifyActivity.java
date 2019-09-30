package com.noobsever.codingcontests.Notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.noobsever.codingcontests.Adapters.NotificationAdapter;
import com.noobsever.codingcontests.DatabaseHandler;
import com.noobsever.codingcontests.Models.Notification;
import com.noobsever.codingcontests.R;

import java.util.ArrayList;
import java.util.List;

public class NotifyActivity extends AppCompatActivity {



    private DatabaseHandler db;
    TextView nonoti;
    private RecyclerView recycleLayout;
    private NotificationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notification Stack");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        nonoti = findViewById(R.id.nonoti);

        db = new DatabaseHandler(this);

        List<Notification> arrayList = new ArrayList<>();
        arrayList = db.GetAllNotifications();

        if(arrayList.isEmpty())
        {
                nonoti.setVisibility(View.VISIBLE);
        }
        else
        {
                nonoti.setVisibility(View.GONE);
        }

        recycleLayout = findViewById(R.id.notify_recycler);
        recycleLayout.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(arrayList,this);
        recycleLayout.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                adapter.RemoveItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recycleLayout);



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
