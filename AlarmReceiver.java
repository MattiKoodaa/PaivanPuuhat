package com.example.sptest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

        private static final String CHANNEL_ID = "CHANNEL_SAMPLE";
        @Override
        public void onReceive(Context context, Intent intent) {


            String message = intent.getStringExtra("message");

            int notificationId = intent.getIntExtra("notificationId", 0);
            //int notificationId =(int) new Date().getTime();//((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

            System.out.println(notificationId+" notify");

            Intent mainIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, mainIntent, 0);

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                CharSequence channelName = "My Notification";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification_important)
                    .setContentTitle("Muista:")
                    .setContentText(message)
                    .setContentIntent(contentIntent)
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(NotificationCompat.DEFAULT_ALL);

            notificationManager.notify(notificationId, builder.build());
        }
    }