package com.example.coderadar.reminderReceiver

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import android.app.PendingIntent
import android.net.Uri
import android.widget.Toast
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.navDeepLink
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentSavedBinding
import com.example.coderadar.ui.ContestActivity
import com.example.coderadar.ui.savedFragment


class AlarmReceiver: BroadcastReceiver() {
    lateinit var builder: NotificationCompat.Builder
    override fun onReceive(context: Context?, p1: Intent?) {
        context?.startService(Intent(context, BackgroundSoundService::class.java))

        val hrefUrl = p1?.getStringExtra("Url")
        val notificationtitle = p1?.getStringExtra("notificationtitle")

        if (context != null) {
            builder = NotificationCompat.Builder(context, "codeRadar")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(notificationtitle)
                .setChannelId("codeRadar")
                .setContentText("Tap to join the contest.")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(Notification.CATEGORY_CALL)
                .setAutoCancel(true)
        }

        val intent = Intent(context, stopingSoundService::class.java)
        intent.putExtra("url", hrefUrl)

        val notificationpendingIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(notificationpendingIntent)

        // Add as notification

        // Add as notification
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        manager!!.notify(0, builder.build())

//        context?.stopService(Intent(context, BackgroundSoundService::class.java))
    }


}