package com.raycai.fluffie.ui.home.product.reviewexpand

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.data.model.ReviewAttachment

class ReviewExpandViewModel() : ViewModel() {

    private val TAG = ReviewExpandViewModel::class.java.simpleName

    val attachmentChanged = MutableLiveData<Boolean>()
    val attachments = ArrayList<ReviewAttachment>()

    val reviewTitle = MutableLiveData<String>()
    val reviewDesc = MutableLiveData<String>()
    val expanded = MutableLiveData(false)
    val showCommentDialog = MutableLiveData(false)


    fun initData() {
        attachments.clear()

        val r1 = ReviewAttachment()
        r1.type = ReviewAttachment.attachmentImage
        attachments.add(r1)

        val r2 = ReviewAttachment()
        r2.type = ReviewAttachment.attachmentImage
        attachments.add(r2)

        val r3 = ReviewAttachment()
        r3.type = ReviewAttachment.attachmentImage
        attachments.add(r3)

        attachmentChanged.postValue(true)

        expanded.observeForever {
            if (it) {
                reviewTitle.postValue("Pretty strong retinol!")
                reviewDesc.postValue(
                    "It's a pretty strong retinol. I'd suggest\n" +
                            "using with caution! I used it for a few weeks\n" +
                            "though and with consistent use it really\n" +
                            "brightened my skin and plumped it up. I \n" +
                            "can definitely see a difference with \n" +
                            "wrinkles too."
                )
            } else {
                reviewTitle.postValue("Pretty strong retinol!")
                reviewDesc.postValue("It's a pretty strong retinol. I'd suggest...")
            }
        }
    }

    fun onCommentClicked() {
        showCommentDialog.postValue(true)
    }

    fun onExpandClicked() {
        expanded.postValue(!expanded.value!!)
    }

}