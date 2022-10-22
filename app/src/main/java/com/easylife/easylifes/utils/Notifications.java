package com.easylife.easylifes.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


import com.easylife.easylifes.R;
import com.easylife.easylifes.userside.activities.MainActivity;
import com.google.firebase.messaging.RemoteMessage;


public class Notifications {

    private final Context  context;

    public Notifications(Context context){
        this.context = context;
    }

    private static final String CHANNEL_ID = "scNotifications";
    private static final String CHANNEL_NAME = "SC Notifications";
    private static final String CHANNEL_DESC = "Sending and Receiving Notifications";


    //This Method will Display Notification and set the Pending Intents
    public static void displayNotifications(Context context, RemoteMessage remoteMessage) {


        String title, text = "";
        Intent intent = new Intent(context, MainActivity.class);

//        if (remoteMessage.getData().get("notificationType").equals("chat")) {

        String receiverID = remoteMessage.getData().get("receiverID");
//        String receiverName = remoteMessage.getData().get("receiverName");
        String receiverName = "Admin";

        String title1 = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        intent = new Intent(context, MainActivity.class);
        title = title1;
        text = message;
        Utilities utilities = new Utilities(context);

        /*utilities.saveString(context, "artist_id", receiverID);
        utilities.saveString(context, "artist_name", receiverName);
        utilities.saveString(context, "artist_image", receiverImage);*/
//        }


        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.splashlogo)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(1, builder.build());

    }

    //This Will create a Channel for the Notification. Called after the Successful Login
    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }




}
