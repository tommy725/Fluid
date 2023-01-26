package com.raycai.fluffie.data.prefs

import android.content.Context

/**
 * Encrypted shared preferences
 *
 * @author  Arvin Jayanake
 * @version 2.0
 * @since   2020-08-18
 */
class Prefs(private val context: Context) {

    companion object {
        private val FILE_KEY = "com.raycai.fluffie"
        private val LOGGED_IN = "p1"
        private val FCM_TOKEN = "p2"
    }

    var loggedIn = getBool(LOGGED_IN)!!
        set(value) = putBool(LOGGED_IN, value)

    var fcmToken = getString(FCM_TOKEN)!!
        set(value) = putString(FCM_TOKEN, value)


    private fun putBool(key: String?, value: Boolean?) {
        var preferences = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE)
        preferences.edit().putString(key, value?.toString()).apply()
    }

    private fun getBool(key: String?): Boolean? {
        var preferences = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE)
        val value: String? = preferences.getString(key, "")
        return value.toBoolean()
    }

    private fun putString(key: String?, value: String?) {
        var preferences = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE)
        preferences.edit().putString(key, value).apply()
    }

    private fun getString(key: String?): String? {
        var preferences = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE)
        val value: String? = preferences.getString(key, "")
        return value
    }

}