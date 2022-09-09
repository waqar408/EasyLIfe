package com.easylife.easylifes.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.easylife.easylifes.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject


class MyFirebaseMessagingService : FirebaseMessagingService() {

    var title = ""
    var message = ""
    var from_id = ""
    var from_name = ""
    var from_image = ""
    var notification_type = ""
    var number = 0
    private val CHANNEL_ID = "scNotifications"
    private val CHANNEL_NAME = "SC Notifications"
    private val CHANNEL_DESC = "Sending and Receiving Notifications"


    private var utilities: Utilities = Utilities(this)
    lateinit var broadcaster: LocalBroadcastManager


    override fun onCreate() {
        super.onCreate()
        broadcaster = LocalBroadcastManager.getInstance(this);

    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Check if message contains a notification payload.
        Log.d(TAG, "Message data payload: ${remoteMessage.data}")


        if (remoteMessage.data.get("notification_type").equals("status_changed")) {
            val params = remoteMessage.data
            val obj = JSONObject(params as Map<*, *>)
            title = obj.getString("title")
            message = obj.getString("message").toString()
            displayNotifications(
                title,message
            )
           //    Notifications.displayNotifications(applicationContext,remoteMessage)
        }else{
            displayNotifications(
            remoteMessage.notification!!.title.toString(),
            remoteMessage.notification!!.body.toString())
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

    fun displayNotifications(title: String, body: String) {
        if (notification_type.equals("chat"))
        {
            /*val notificationIntent = Intent(this, InboxActivity::class.java)
            notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
                    or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            notificationIntent.putExtra("title", title)
            notificationIntent.putExtra("message", message)
            notificationIntent.putExtra("fromId", from_id)
            notificationIntent.putExtra("fromName", from_name)
            notificationIntent.putExtra("fromImage", from_image)
            val intent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )*/
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
            val managerCompat = NotificationManagerCompat.from(applicationContext)
            managerCompat.notify(124, builder.build())
        }else{
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX)
            val managerCompat = NotificationManagerCompat.from(applicationContext)
            managerCompat.notify(124, builder.build())
        }

    }


    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    /*@Noman manay phone number ko ab signup ki screen may shift kardiya ha....                                                                                       login with google integrate kardiya ha but abhi uski api may ek key add honi ha to iski wajah say manay usko abhi trainer side par redirect karwa diya ha....                                                                                                                                                                           app name change kar diya ha                                                                                                                                                                         user ko otp verify karwanay ke bad gender selection wali screen par redirect karwa diya ha                                                                         login with facebook rahta ha wo p*/
}
