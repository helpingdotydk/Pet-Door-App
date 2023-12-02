package com.example.testapp;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "Push-Event";
    public static final String CHANNEL_ID = String.valueOf(R.string.default_notification_channel_id);
    public static final String CHANNEL_NAME = "com.example.androidappfortesting";

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a Notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String description = remoteMessage.getNotification().getBody();
            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Description: " + description);


//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Pet Door Notification", NotificationManager.IMPORTANCE_DEFAULT);
//
//            getSystemService(NotificationManager.class).createNotificationChannel(channel);
//
//            Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID);
//            notification.setContentTitle(title);
//            notification.setContentText(description);
//            notification.setAutoCancel(true);
//            notification.setSmallIcon(android.R.drawable.ic_dialog_alert);
//
//            NotificationManagerCompat.from(this).notify(0, notification.build());

            createNotificationChannel();
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default_notification_channel_id")
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "default_notification_channel_id",
                    "Channel name",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel description");

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

}