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
import com.example.CodeRadar.R
import com.example.coderadar.ui.ContestActivity


class AlarmReceiver: BroadcastReceiver() {
    lateinit var builder: NotificationCompat.Builder
    override fun onReceive(context: Context?, p1: Intent?) {
        context?.startService(Intent(context, BackgroundSoundService::class.java))

        val hrefUrl = p1?.getStringExtra("Url")

        if (context != null) {
            builder = NotificationCompat.Builder(context, "codeRadar")

                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("Notifications Example")
                .setContentText("This is a test notification")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true)
        }

        val notificationIntent = Intent()
        notificationIntent.setPackage("com.android.chrome")
        notificationIntent.action = Intent.ACTION_VIEW
        notificationIntent.setData(Uri.parse(hrefUrl))
        val contentIntent = PendingIntent.getActivity(
            context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        builder.setContentIntent(contentIntent)

        // Add as notification

        // Add as notification
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        manager!!.notify(0, builder.build())

    }


}