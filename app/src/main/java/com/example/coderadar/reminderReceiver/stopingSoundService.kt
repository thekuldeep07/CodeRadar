package com.example.coderadar.reminderReceiver

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import com.example.CodeRadar.R

class stopingSoundService: Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        stopService(Intent(this, BackgroundSoundService::class.java))
        stopService(Intent(this, NotificationSenderBackground::class.java))

        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        stopService(Intent(this, BackgroundSoundService::class.java))
        stopService(Intent(this, NotificationSenderBackground::class.java))
        val hrefUrl = intent?.getStringExtra("url")
        val startchrome = Intent()
        startchrome.setPackage("com.android.chrome")
        startchrome.action = Intent.ACTION_VIEW
        startchrome.data = (Uri.parse(hrefUrl))
        startActivity(startchrome)
        return startId
    }

}