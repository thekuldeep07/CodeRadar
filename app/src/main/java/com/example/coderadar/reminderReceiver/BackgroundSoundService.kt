package com.example.coderadar.reminderReceiver

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.CodeRadar.R

class BackgroundSoundService: Service() {
    var mp: MediaPlayer? = null
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        mp = MediaPlayer.create(applicationContext, R.raw.alarm)
        mp?.isLooping = true
        mp?.setVolume(100f, 100f)

        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mp?.start()
        return startId
    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.stop()
        mp?.release()
    }

}