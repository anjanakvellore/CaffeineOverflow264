package com.example.caffeineoverflow264.repository.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){

        int NOTIFICATION_ID = 1;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("Caffeine Reminder")
                .setContentText("Hey! It's time for a Coffee! :D")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}
