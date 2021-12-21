package com.example.coderadar.reminderReceiver

import android.content.Context
import android.media.MediaPlayer
import com.example.CodeRadar.R


class AlarmAnalog {
    private var mMediaPlayer: MediaPlayer? = null

    companion object {
        var sInstance = AlarmAnalog()
        fun getInstance(): AlarmAnalog? {
            if (sInstance == null) {
                sInstance = AlarmAnalog()
            }
            return sInstance
        }
    }


    fun playMusic(context: Context) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.alarm)
        mMediaPlayer!!.start()
    }

    fun stopMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.seekTo(0)
        }
    }

}