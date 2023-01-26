package com.raycai.fluffie.data.model

import android.net.Uri

class SelectedFile {

    var mimeType: String? = null
    var url :String? = ""

    fun isVideo():Boolean{
        if (mimeType != null){
            return mimeType!!.contains("video")
        }
        return false
    }

    fun isImage():Boolean{
        if (mimeType != null){
            return mimeType!!.contains("image")
        }
        return false
    }

}