package com.dam.bestexpensetracker.data.constant

class Const {

    companion object {

        val DB_NAME = "fluffie.db"

        val GOOGLE_WEB_CLIENT_ID =
            "623294694865-se4lre7s80sr9vhm3osr1sonau34l6e7.apps.googleusercontent.com"

        val DATE_FORMAT_DISPLAY = "yyyy-MMM-dd"
        val TIME_FORMAT_DISPLAY = "hh.mm aa"
        val HOME_DATE_FORMAT = "dd MMMM yyyy"
        val HOME_MONTH_FORMAT = "MMMM yyyy"
        val SUMMARY_DATE_FORMAT = "dd/MM/yyyy"

        //fcm
        val FCM_TOPIC_ALL_USERS = "all_users"
        val FCM_TOPIC_AUTO_BACKUP = "auto_backup"

        val API_URL = "http://13.127.125.105:5001"
        val DEVELOPER_MODE = false
    }
}