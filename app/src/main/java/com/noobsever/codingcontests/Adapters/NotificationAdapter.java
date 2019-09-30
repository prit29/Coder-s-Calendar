package com.noobsever.codingcontests.Adapters;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.noobsever.codingcontests.DatabaseHandler;
import com.noobsever.codingcontests.Models.Notification;
import com.noobsever.codingcontests.Notifications.AlertReceiver;
import com.noobsever.codingcontests.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.RecycleViewHolder> {

    private List<Notification> data;
    public Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


    public NotificationAdapter(List<Notification> data, Context ccc) {
        this.data = data;
        this.context = ccc;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.notification_item_view, viewGroup, false);
        return new RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewHolder recycleViewHolder, int i) {



        recycleViewHolder.event_name.setText(data.get(i).getEvent());
        recycleViewHolder.event_time.setText(data.get(i).getTime());




    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void RemoveItem(int id)
    {


        DatabaseHandler db = new DatabaseHandler(context);
        db.DeleteNotification(data.get(id));
        CancelAlarm(data.get(id).getNotiId());
        data.remove(id);
        notifyDataSetChanged();
        return;

    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {


        TextView event_name,event_time;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

                event_name = itemView.findViewById(R.id.event_name);
                event_time = itemView.findViewById(R.id.evenet_time);

        }

    }

    private void CancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}