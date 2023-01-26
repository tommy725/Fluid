package com.raycai.fluffie.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.ui.home.product.review.attachment.AttachmentListener
import com.raycai.fluffie.ui.home.product.review.attachment.ReviewAttachmentFragment

class ReviewAttachmentPagerAdapter(
    var attachments: ArrayList<ReviewAttachment>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return attachments.size
    }

    override fun getItem(position: Int): Fragment {
        val a = attachments[position]
        return ReviewAttachmentFragment.newInstance(a)
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "OBJECT ${(position + 1)}"
    }
}