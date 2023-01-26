package com.raycai.fluffie.ui.home.product.prosorcon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.google.android.material.tabs.TabLayout
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.model.Pros
import com.raycai.fluffie.data.model.Review
import com.raycai.fluffie.databinding.FragmentProsOrConBinding
import com.raycai.fluffie.ui.home.product.claims.ClaimsFragment
import me.zhanghai.android.materialratingbar.MaterialRatingBar

class ProsOrConFragment : BaseFragment() {

    private val TAG = ClaimsFragment::class.java.simpleName
    private lateinit var viewModel: ProsOrConViewModel
    private lateinit var binding: FragmentProsOrConBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProsOrConViewModel::class.java]
        binding = FragmentProsOrConBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        initData()
        return binding.root
    }

    private fun setObservers() {
        viewModel.tabsChanged.observeForever {
            binding.tabLayout.removeAllTabs()
            viewModel.tabs.forEach {
                val tab: TabLayout.Tab = binding.tabLayout.newTab()
                tab.text = it

                binding.tabLayout.addTab(tab, true)
            }
            binding.tabLayout.getTabAt(0)?.select()
        }

        viewModel.selectedProduct.observeForever {
            if (it != null) {
                binding.tvTitle.text = it.title
            }
        }
    }

    private fun initData() {
        viewModel.initData()

        viewModel.selectedProduct.postValue((activity as HomeActivity).selectedProduct)
        viewModel.selectedPros.postValue((activity as HomeActivity).selectedPros)
        viewModel.selectedCon.postValue((activity as HomeActivity).selectedCon)
        loadPros()
        loadReviews()
    }


    fun onApplySkinClicked(view: View) {
        (activity as HomeActivity).loadProsOrConFilterFragment(true)
    }

    fun onDownArrowPressed(view: View) {

    }

    fun onShowMoreReviewsClicked(view: View) {
        viewModel.isShowingMoreReviews = !viewModel.isShowingMoreReviews
        loadReviews()
        if (viewModel.isShowingMoreReviews) {
            binding.ivShowArrowReviews.setImageResource(R.drawable.ic_up_arrow)
            binding.tvShowMoreReviews.text = "Show Less"
        } else {
            binding.ivShowArrowReviews.setImageResource(R.drawable.ic_down)
            binding.tvShowMoreReviews.text = "Show More"
        }
    }

    fun onShowMoreClicked(view: View) {
        viewModel.isShowingMore = !viewModel.isShowingMore
        loadPros()
        if (viewModel.isShowingMore) {
            binding.ivShowArrow.setImageResource(R.drawable.ic_up_arrow)
            binding.tvShowMore.text = "Show Less"
        } else {
            binding.ivShowArrow.setImageResource(R.drawable.ic_down)
            binding.tvShowMore.text = "Show More"
        }
    }

    private fun loadReviews() {
        var idx = 0
        binding.layoutReviews.removeAllViews()
        viewModel.reviews.forEach {
            if (viewModel.isShowingMoreReviews) {
                addReview(it)
            } else {
                if (idx < viewModel.minReviews) {
                    addReview(it)
                }
            }
            idx++
        }
    }

    private fun addReview(r: Review) {
        val view = layoutInflater.inflate(R.layout.layout_reciew, null)
        val materialRatingBar: MaterialRatingBar = view.findViewById(R.id.materialRatingBar)
        val tvTimeAgo: TextView = view.findViewById(R.id.tvTimeAgo)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val tvDesc2: TextView = view.findViewById(R.id.tvDesc2)
        materialRatingBar.rating = r.rating.toFloat()
        tvTimeAgo.text = r.timeAgo
        tvDesc.text = r.desc
        tvDesc2.text = r.subDesc
        binding.layoutReviews.addView(view)
    }


    private fun loadPros() {
        var idx = 0
        binding.layoutPros.removeAllViews()
        viewModel.prosList.forEach {
            if (viewModel.isShowingMore) {
                addPros(it)
            } else {
                if (idx < viewModel.minPros) {
                    addPros(it)
                }
            }
            idx++
        }
    }

    private fun addPros(p: Pros) {
        val view = layoutInflater.inflate(R.layout.layout_pros_or_con_expand, null)
        val tvText: TextView = view.findViewById(R.id.tvText)
        val tvVal: TextView = view.findViewById(R.id.tvVal)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        progressBar.progress = p.value.toInt()
        tvText.text = p.txt
        tvVal.text = p.value
        binding.layoutPros.addView(view)
    }

    fun goBack(view: View) {
        (activity as HomeActivity).onBackPressed()
    }


    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}