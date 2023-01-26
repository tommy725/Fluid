package com.raycai.fluffie.ui.home.product.review.attachment

import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.data.model.SelectedFile

interface AttachmentListener {

    fun onClicked(a: ReviewAttachment)

    fun onRemoveAttachment(a: SelectedFile?)


}