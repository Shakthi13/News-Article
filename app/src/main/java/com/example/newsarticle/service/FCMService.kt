package com.example.newsarticle.service

import android.app.NotificationChannel
import android.app.NotificationChannel.DEFAULT_CHANNEL_ID
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.newsarticle.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM", "onMessageReceived: ${message.notification?.title}")
        //build notification
        getFireBaseMessage(message.notification?.title.toString(),message.notification?.body.toString())
    }

    private fun getFireBaseMessage(title:String, msg:String){
        val builder = NotificationCompat.Builder(this, "firebase msg")
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setSubText(msg)
            .setAutoCancel(true)

        val manager = NotificationManagerCompat.from(this)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                DEFAULT_CHANNEL_ID,
                "FCM",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
        manager.notify(101,builder.build())
    }
}
