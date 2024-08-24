package com.mostafatamer.postlibrary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mostafatamer.postlibrary.activity.MainActivity

fun Context.pushNotification(title: String?, message: String?) {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val intent = Intent(this, MainActivity::class.java)
    val pendingIntent = PendingIntent.getActivity(
        this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notificationBuilder = NotificationCompat.Builder(this, "default_channel_id")
        .setSmallIcon(R.drawable.app_icon)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)

    val channel = NotificationChannel(
        "default_channel_id",
        "Default Channel",
        NotificationManager.IMPORTANCE_HIGH
    )

    notificationManager.createNotificationChannel(channel)

    notificationManager.notify(0, notificationBuilder.build())
}