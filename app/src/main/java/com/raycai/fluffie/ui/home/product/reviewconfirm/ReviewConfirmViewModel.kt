package com.raycai.fluffie.ui.home.product.reviewconfirm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.data.model.Product
import com.raycai.fluffie.data.model.ReviewAttachment

class ReviewConfirmViewModel() : ViewModel() {

    private val TAG = ReviewConfirmViewModel::class.java.simpleName

    val attachmentChanged = MutableLiveData<Boolean>()
    val attachments = ArrayList<ReviewAttachment>()
    var rating: Double = 0.0
    val ratingText = MutableLiveData<String>()
    var product: Product? = null

    fun initData() {
        attachments.clear()

        MyApp.instance.selectedFiles.forEach {
            val a = ReviewAttachment()
            a.enableRemove = false

            if (it.isImage()) {
                a.type = ReviewAttachment.attachmentImage
            } else if (it.isVideo()) {
                a.type = ReviewAttachment.attachmentVideo
            }

            if (it.url != null) {
                a.url = it.url.toString()
            }


            attachments.add(a)
        }

        rating = MyApp.instance.rating

        attachmentChanged.postValue(true)
    }


}