package com.bahaa.assignment3

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class MyNotification(val context: Context) {
    val NOTIFICATION_ID = 1
    val CHANNEL_ID = "channel id"

    fun showNotification(id: Int, title: String, message: String, intent: Intent){
        val pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        val notification = builder
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, "Channel Noti", NotificationManager.IMPORTANCE_HIGH)
            channel.setShowBadge(true)
            channel.description = message
            channel.enableLights(true)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(id, notification)
    }

    fun getNotificationId(): Int{
        return NOTIFICATION_ID
    }

    fun getNotification(context: Context, title: String, message: String): Notification?{
        val CHANNEL_ID = "channel id2"

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(CHANNEL_ID, "Channel SignIn", NotificationManager.IMPORTANCE_HIGH)
            channel.setShowBadge(true)
            channel.description = "SignIn"
            channel.enableLights(true)
            channel.enableVibration(true)

            assert(notificationManager != null)
            notificationManager.createNotificationChannel(channel)

            val pendingIntent = PendingIntent.getActivity(context, 0, Intent(context, SignIn::class.java), 0)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            val notification = builder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build()
            return notification
        }else{
            val pendingIntent = PendingIntent.getActivity(context, 0, Intent(context, SignIn::class.java), 0)

            val builder = NotificationCompat.Builder(context)
            val notification = builder
                .setCategory(Notification.CATEGORY_SERVICE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .build()
            return notification
        }
    }

}