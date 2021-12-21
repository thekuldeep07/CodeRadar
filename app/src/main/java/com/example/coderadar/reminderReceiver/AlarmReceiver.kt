package com.example.coderadar.reminderReceiver

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationManager
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import android.app.PendingIntent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.navDeepLink
import com.example.CodeRadar.R
import com.example.CodeRadar.databinding.FragmentSavedBinding
import com.example.coderadar.MainActivity
import com.example.coderadar.ui.ContestActivity
import com.example.coderadar.ui.savedFragment


class AlarmReceiver: BroadcastReceiver() {
    lateinit var builder: NotificationCompat.Builder
    @SuppressLint("LaunchActivityFromNotification")
    override fun onReceive(context: Context?, p1: Intent?) {

        AlarmAnalog.getInstance()?.playMusic(context!!)

        createNotificationChannel(context!!, NotificationManager.IMPORTANCE_HIGH, "CodeRadar", "Something for checking.")
        val notificationtitle = p1?.getStringExtra("notificationtitle")
        val href = p1?.getStringExtra("Url")

        builder = NotificationCompat.Builder(context, "CodeRadar")
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notificationtitle)
            .setChannelId("CodeRadar")
            .setContentText("Tap to join the contest.")
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)

        val startchrome = Intent(context, StopMusicReciever::class.java)
        startchrome.putExtra("Url", href)

        val notificationpendingIntent = PendingIntent.getBroadcast(context, 0, startchrome, 0)
        builder.setContentIntent(notificationpendingIntent)

        // Add as notification

        // Add as notification
        val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        manager!!.notify(999, builder.build())

    }

    private fun createNotificationChannel(context: Context, importance: Int, name: String, description: String) {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "CodeRadar"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description

            // 3
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}