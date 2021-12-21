package com.example.coderadar.reminderReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

class StopMusicReciever: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        AlarmAnalog.getInstance()?.stopMusic()
        val href = p1?.getStringExtra("Url")

        val startchrome = Intent()
        startchrome.setPackage("com.android.chrome")
        startchrome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startchrome.action = Intent.ACTION_VIEW
        startchrome.data = Uri.parse(href)
        p0?.startActivity(startchrome)
    }
}