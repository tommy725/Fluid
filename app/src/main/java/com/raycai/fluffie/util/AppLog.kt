package com.dam.bestexpensetracker.util

import android.util.Log
import com.raycai.fluffie.BuildConfig
import java.lang.Exception

class AppLog {

    companion object {
        fun log(tag: String, msg: String) {
            if (BuildConfig.DEBUG) {
                Log.i(tag, msg)
            }
        }

        fun error(e: Exception) {
            if (BuildConfig.DEBUG) {
                e.printStackTrace()
            }
        }
    }
}