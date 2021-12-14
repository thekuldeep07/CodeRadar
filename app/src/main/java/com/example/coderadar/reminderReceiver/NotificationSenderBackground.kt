package com.example.coderadar.reminderReceiver

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class NotificationSenderBackground: Service() {
    var href: String? = null
    var notificationtitle: String? = null
    var remainingTime: Long? = null
    var pendingIntent: PendingIntent? = null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(applicationContext, NotificationManager.IMPORTANCE_HIGH, true, "CodeRadar", "Something for checking.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && (intent.hasExtra("hrefurl"))){
            href = intent.getStringExtra("hrefurl")
//            remainingTime = intent.getLongExtra("remaining", 0)
            remainingTime = 5000
            notificationtitle = intent.getStringExtra("notificationtitle")
        }
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
        intent.putExtra("Url", href)
        intent.putExtra("notificationtitle", notificationtitle)
        pendingIntent = PendingIntent.getBroadcast(
            applicationContext, 234, intent, 0
        )


        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, remainingTime!!,
        pendingIntent)

        return startId
    }

    private fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description: String) {
        // 1
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // 2
            val channelId = "codeRadar"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description

            // 3
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}