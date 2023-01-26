package com.raycai.fluffie.data.model

class ReviewAttachment {

    var type = attachmentAdd
    var url:String = ""
    var enableRemove = false

    companion object {
        val attachmentAdd = 0
        val attachmentImage = 1
        val attachmentVideo = 2
    }

    fun isAddAttachment(): Boolean {
        return type == attachmentAdd
    }

    fun isVideo(): Boolean{
        return type == attachmentVideo
    }

    fun isImage():Boolean{
        return type == attachmentImage
    }

}