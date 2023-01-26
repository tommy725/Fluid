package com.raycai.fluffie.app

import android.app.Application
import android.util.Log
import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.data.model.SelectedFile
import java.io.File

class MyApp : Application() {

    private val TAG = MyApp::class.java.simpleName

    companion object {
        lateinit var instance: MyApp
    }

    //Review data backup
    var maxSelectableFiles = 5
    var selectedFiles: ArrayList<SelectedFile> = ArrayList()
    var recording: File? = null
    var rating: Double = 0.0

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun log(msg: String) {
        Log.i(TAG, msg)
    }
}