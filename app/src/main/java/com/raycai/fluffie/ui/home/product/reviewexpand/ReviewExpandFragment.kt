package com.raycai.fluffie.ui.home.product.reviewexpand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.adapter.ReviewAttachmentPagerAdapter
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.model.ReviewAttachment
import com.raycai.fluffie.data.model.SelectedFile
import com.raycai.fluffie.databinding.FragmentReviewExpandBinding
import com.raycai.fluffie.ui.bottomsheet.CommentBottomSheetDialog
import com.raycai.fluffie.ui.home.product.review.attachment.AttachmentListener

class ReviewExpandFragment : BaseFragment() {

    private val TAG = ReviewExpandFragment::class.java.simpleName
    private lateinit var viewModel: ReviewExpandViewModel
    private lateinit var binding: FragmentReviewExpandBinding

    private var attachmentPagerAdapter: ReviewAttachmentPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ReviewExpandViewModel::class.java]
        binding = FragmentReviewExpandBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        initData()
        return binding.root
    }

    private fun setObservers() {
        viewModel.showCommentDialog.observeForever {
            if (it){
                showBottomSheetDialog()
            }
        }

        viewModel.attachmentChanged.observeForever {
            setAttachments()
        }
    }

    private fun initData() {
        viewModel.initData()
    }

    private fun setAttachments() {
        attachmentPagerAdapter = ReviewAttachmentPagerAdapter(viewModel.attachments, childFragmentManager)
        binding.viewPager.adapter = attachmentPagerAdapter
        binding.viewPager.clipToPadding = false
        binding.viewPager.setPadding(260, 0, 260, 0)
        binding.viewPager.pageMargin = 4.dpToPx.toInt()
        binding.viewPager.offscreenPageLimit = 1
        binding.dotsIndicator.attachTo(binding.viewPager)

        (activity as HomeActivity).reviewAttachmentListener = object : AttachmentListener {
            override fun onClicked(a: ReviewAttachment) {

            }

            override fun onRemoveAttachment(a: SelectedFile?) {

            }
        }

        val rr = ResourcesCompat.getFont(requireContext(), R.font.roboto_regular)
        binding.c1.typeface = rr
        binding.c2.typeface = rr
        binding.c3.typeface = rr
    }

    fun onExpandClicked(view: View){
        viewModel.onExpandClicked()
    }

    fun onChip1Clicked(view: View){

    }

    fun onChip2Clicked(view: View){

    }

    fun onChip3Clicked(view: View){

    }

    fun onCommentClicked(view: View){
        viewModel.onCommentClicked()
    }

    fun onHeartClicked(view: View){

    }

    fun onFollowClicked(view: View){

    }

    fun onPlusClicked(view: View){

    }

    fun goBackClicked(view: View){
        (activity as HomeActivity).onBackPressed()
    }

    private fun showBottomSheetDialog(){
        val dialog = CommentBottomSheetDialog()
        dialog.show(
            childFragmentManager,
            CommentBottomSheetDialog::class.java.canonicalName
        )
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}