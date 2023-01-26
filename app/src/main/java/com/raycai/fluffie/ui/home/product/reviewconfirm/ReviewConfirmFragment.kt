package com.raycai.fluffie.ui.home.product.reviewconfirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.adapter.ReviewAttachmentPagerAdapter
import com.raycai.fluffie.app.MyApp
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.data.model.SelectedFile
import com.raycai.fluffie.databinding.FragmentBrowseBinding
import com.raycai.fluffie.databinding.FragmentHomeBinding
import com.raycai.fluffie.databinding.FragmentIndexBinding
import com.raycai.fluffie.databinding.FragmentProfileBinding
import com.raycai.fluffie.databinding.FragmentReviewConfirmBinding
import com.raycai.fluffie.ui.home.product.review.attachment.AttachmentListener

class ReviewConfirmFragment : BaseFragment() {

    private val TAG = ReviewConfirmFragment::class.java.simpleName
    private lateinit var viewModel: ReviewConfirmViewModel
    private lateinit var binding: FragmentReviewConfirmBinding

    private var attachmentPagerAdapter: ReviewAttachmentPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ReviewConfirmViewModel::class.java]
        binding = FragmentReviewConfirmBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        initData()
        initUi()
        return binding.root
    }

    private fun initUi() {
        binding.materialRatingBar.rating = viewModel.rating.toFloat()
    }

    private fun setObservers() {
        viewModel.attachmentChanged.observeForever {
            setAttachments()
        }
    }

    private fun initData() {
        viewModel.initData()
    }

    private fun setAttachments() {
        attachmentPagerAdapter =
            ReviewAttachmentPagerAdapter(viewModel.attachments, childFragmentManager)
        binding.viewPager.adapter = attachmentPagerAdapter
        binding.viewPager.clipToPadding = false
        binding.viewPager.setPadding(260, 0, 260, 0)
        binding.viewPager.pageMargin = 4.dpToPx.toInt()
        binding.viewPager.offscreenPageLimit = 1
        binding.dotsIndicator.attachTo(binding.viewPager)
    }

    fun onPostClicked(view: View){

    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

    fun goBack(view: View) {
        (activity as HomeActivity).onBackPressed()
    }
}