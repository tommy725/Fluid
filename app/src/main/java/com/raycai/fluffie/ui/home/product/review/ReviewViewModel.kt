package com.raycai.fluffie.ui.home.product.review

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.data.model.Product
import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.data.model.SelectedFile
import com.raycai.fluffie.http.response.ProductDetailResponse
import java.net.URLConnection


class ReviewViewModel : ViewModel() {

    private val TAG = ReviewViewModel::class.java.simpleName

    val attachmentChanged = MutableLiveData<Boolean>()
    val attachments = ArrayList<ReviewAttachment>()
    val loadGallery = MutableLiveData<Boolean>()
    val actionEnabled = MutableLiveData(false)
    var rating: Double = 0.0
    val ratingText = MutableLiveData<String>()
    var product: ProductDetailResponse.ProductDetail? = null

    fun onRemoveAttachment(a: SelectedFile?) {
        if (a != null) {
            MyApp.instance.selectedFiles.remove(a)
            initData()
        }
    }

    fun onAttachClicked() {
        var aCount = 0
        attachments.forEach {
            if (!it.isAddAttachment()) {
                aCount++
            }
        }

        MyApp.instance.maxSelectableFiles = 5 - aCount


        loadGallery.postValue(true)
    }

    fun updateRatingText() {
        val txt = "What would you rate\n${product?.brand!!.brand}?"
        ratingText.postValue(txt)
    }

    fun initData() {
        attachments.clear()

        MyApp.instance.selectedFiles.forEach {
            val a = ReviewAttachment()
            if (it.isImage()) {
                a.type = ReviewAttachment.attachmentImage
            } else if (it.isVideo()) {
                a.type = ReviewAttachment.attachmentVideo
            }

            if (it.url != null) {
                a.url = it.url.toString()
            }

            a.enableRemove = true
            attachments.add(a)
        }

        if (attachments.size <= 4) {
            val addAttachment = ReviewAttachment()
            addAttachment.enableRemove = false
            addAttachment.type = ReviewAttachment.attachmentAdd
            attachments.add(addAttachment)
        }

        attachmentChanged.postValue(true)

        actionEnabled.postValue(attachments.size >= 2)
    }


    private fun log(msg: String) {
        Log.i(TAG, msg)
    }


}