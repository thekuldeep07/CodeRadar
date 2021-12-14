package com.example.coderadar.reminderReceiver

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import com.example.CodeRadar.R

class stopingSoundService: Service() {
    private var hrefUrl: String? = null
    private var startchrome: Intent? = null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        stopService(Intent(this, BackgroundSoundService::class.java))
        stopService(Intent(this, NotificationSenderBackground::class.java))

        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        hrefUrl = intent?.getStringExtra("url")
        startchrome = Intent()
        startchrome?.setPackage("com.android.chrome")
        startchrome?.action = Intent.ACTION_VIEW
        startchrome?.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startchrome?.data = (Uri.parse(hrefUrl))
        startActivity(startchrome)
        stopService(Intent(this, BackgroundSoundService::class.java))
        stopService(Intent(this, NotificationSenderBackground::class.java))
        stopSelf()
        return startId
    }

}