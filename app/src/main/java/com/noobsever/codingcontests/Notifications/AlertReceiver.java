package com.noobsever.codingcontests.Notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String s= intent.getStringExtra("contest");
        NotificationHelper notificationHelper = new NotificationHelper(context,s);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        final int _id = (int) System.currentTimeMillis();
        notificationHelper.getManager().notify(_id, nb.build());
    }
}