package com.noobsever.codingcontests.Notifications;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.noobsever.codingcontests.MainActivity;
import com.noobsever.codingcontests.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;
    private  String ss;

    Context cc;
    public NotificationHelper(Context base,String s) {
        super(base);
        this.cc = base;
        this.ss =s;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification() {

        Intent notificationIntent = new Intent(cc, MainActivity.class);
        PendingIntent intent = PendingIntent.getActivity(cc, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("Reminder!!")
                .setContentText(ss)
                .setStyle(new NotificationCompat.BigTextStyle())
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.mf))
                //.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),R.drawable.mf)))
                .setSmallIcon(R.drawable.ic_launcher_background).setContentIntent(intent);


    }
}