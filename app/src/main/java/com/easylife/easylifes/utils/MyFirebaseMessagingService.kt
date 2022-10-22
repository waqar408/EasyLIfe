package com.easylife.easylifes.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.easylife.easylifes.R
import com.easylife.easylifes.userside.activities.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    var title = ""
    var message = ""
    var number = 0
    private val CHANNEL_ID = "scNotifications"


    private lateinit var broadcaster: LocalBroadcastManager


    override fun onCreate() {
        super.onCreate()
        broadcaster = LocalBroadcastManager.getInstance(this)

    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Check if message contains a notification payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data["notification_type"]}")
            if (remoteMessage.data["notification_type"].equals("subscribe_trainer")) {
                val params = remoteMessage.data
                val obj = JSONObject(params as Map<*, *>)
                title = obj.getString("title")
                message = obj.getString("message").toString()
                displayNotifications(applicationContext,remoteMessage)

            }else if (remoteMessage.data["notification_type"].equals("meal_assigned")){
                val params = remoteMessage.data
                val obj = JSONObject(params as Map<*, *> )
                title = obj.getString("title")
                message = obj.getString("message")
                displayNotifications(applicationContext,remoteMessage)

            }else if (remoteMessage.data["notification_type"].equals("workout_assigned")){
                val params = remoteMessage.data
                val obj = JSONObject(params as Map<*, *> )
                title = obj.getString("title")
                message = obj.getString("message")
                displayNotifications(applicationContext,remoteMessage)

            }else if (remoteMessage.data["notification_type"].equals("admin_notification")){
                val params = remoteMessage.data
                val obj = JSONObject(params as Map<*, *> )
                title = obj.getString("title")
                message = obj.getString("message")
                displayNotifications(applicationContext,remoteMessage)

            }else{
                val params = remoteMessage.data
                val obj = JSONObject(params as Map<*, *> )
                title = obj.getString("title")
                message = obj.getString("message")
                displayNotifications(applicationContext,remoteMessage)

            }
        }



    }

    override fun onNewToken(token: String) {

        Log.d(TAG, "Refreshed token: $token")
        val sharedPref: SharedPreferences =
            getSharedPreferences("TOKEN", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("FCM_TOKEN", token)
        editor.apply()
    }



    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    private fun displayNotifications(context: Context?, remoteMessage: RemoteMessage) {
        val title: String?
        val text: String

        val title1 = remoteMessage.data["title"]
        val message = remoteMessage.data["message"]
        val intent = Intent(context, MainActivity::class.java)
        title = title1
        text = message!!


        val pendingIntent = PendingIntent.getActivity(
            context,
            100,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
        val builder = NotificationCompat.Builder(
            context!!,CHANNEL_ID
        )
            .setSmallIcon(R.drawable.splashlogo)
            .setContentTitle(title)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_MAX)
        val managerCompat = NotificationManagerCompat.from(context)
        managerCompat.notify(1, builder.build())
    }

}