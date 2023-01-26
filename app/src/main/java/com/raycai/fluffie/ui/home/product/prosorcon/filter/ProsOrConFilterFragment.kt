package com.raycai.fluffie.ui.home.product.prosorcon.filter

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.allViews
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.google.android.flexbox.FlexboxLayout
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentProsOrConFilterBinding
import com.raycai.fluffie.ui.home.product.claims.ClaimsFragment
import com.raycai.fluffie.util.AppConst

class ProsOrConFilterFragment : BaseFragment() {

    private val TAG = ClaimsFragment::class.java.simpleName
    private lateinit var viewModel: ProsOrConFilterViewModel
    private lateinit var binding: FragmentProsOrConFilterBinding

    //Resources
    private lateinit var fontBold: Typeface
    private lateinit var fontLite: Typeface
    private var colorRose = 0
    private var colorWhite = 0
    private var colorGray = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProsOrConFilterViewModel::class.java]
        binding = FragmentProsOrConFilterBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.btnActionEnable.observeForever {
            binding.btnShowProducts.isEnabled = it
        }

        viewModel.selectedSkinType.observeForever { skinType ->
            resetSkinTypes()
            if (skinType != null) {
                getTvs(binding.flSkinType).forEach {
                    if (it.text.toString() == skinType) {
                        selectTextView(it)
                    }
                }
            }
        }

        viewModel.acneProneSelected.observeForever {
            if (it != null) {
                if (it) {
                    selectTextView(binding.tvAcneProne)
                } else {
                    resetTextView(binding.tvAcneProne)
                }
            }
        }

        viewModel.prosOrCon.observeForever { age ->
            resetProsOrCon()
            if (age != null) {
                getTvs(binding.flProsOrCon).forEach {
                    if (it.text.toString() == age) {
                        selectTextView(it)
                    }
                }
            }
        }

        viewModel.selectedAge.observeForever { age ->
            resetAges()
            if (age != null) {
                getTvs(binding.flAge).forEach {
                    if (it.text.toString() == age) {
                        selectTextView(it)
                    }
                }
            }
        }

        viewModel.selectedLocation.observeForever { age ->
            resetLocations()
            if (age != null) {
                getTvs(binding.flLocation).forEach {
                    if (it.text.toString() == age) {
                        selectTextView(it)
                    }
                }
            }
        }

        viewModel.selectedProductAspect.observeForever { age ->
            resetProductAspects()
            if (age != null) {
                getTvs(binding.flProductAspect).forEach {
                    if (it.text.toString() == age) {
                        selectTextView(it)
                    }
                }
            }
        }

        viewModel.onReviewResourceChanged.observeForever {
            resetReviewSource()
            getTvs(binding.flReviewSource).forEach { tv ->
                viewModel.selectedReviewSources.forEach {
                    if (tv.text.toString() == it) {
                        selectTextView(tv)
                    }
                }
            }
        }

        viewModel.selectedFluffieFilter.observeForever { age ->
            resetFluffieFilter()
            if (age != null) {
                getTvs(binding.flFluffieFilter).forEach {
                    if (it.text.toString() == age) {
                        selectTextView(it)
                    }
                }
            }
        }
    }

    private fun initData() {
        (activity as HomeActivity).setBottomNavigationVisibility(false)
        viewModel.initData()
        colorRose = ContextCompat.getColor(requireContext(), R.color.rose)
        colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        colorGray = ContextCompat.getColor(requireContext(), R.color.gray)
        fontBold = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)!!
        fontLite = ResourcesCompat.getFont(requireContext(), R.font.roboto_light)!!

        //Pros or Cons
        binding.flProsOrCon.removeAllViews()
        AppConst.prosOrCon.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.prosOrCon.postValue(filter)
            }
            binding.flProsOrCon.addView(root)
        }

        //Skin types
        binding.flSkinType.removeAllViews()
        AppConst.skinTypes.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.selectedSkinType.postValue(filter)
            }
            binding.flSkinType.addView(root)
        }

        //Ages
        binding.flAge.removeAllViews()
        AppConst.ages.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.selectedAge.postValue(filter)
            }
            binding.flAge.addView(root)
        }

        //Locations
        binding.flLocation.removeAllViews()
        AppConst.locations.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.selectedLocation.postValue(filter)
            }
            binding.flLocation.addView(root)
        }

        //Product Aspect
        binding.flProductAspect.removeAllViews()
        AppConst.productAspects.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.selectedProductAspect.postValue(filter)
            }
            binding.flProductAspect.addView(root)
        }

        //Review Source
        binding.flReviewSource.removeAllViews()
        AppConst.reviewSource.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                if (filter == "Select all") {
                    viewModel.selectAllReviewResource()
                } else {
                    viewModel.selectedReviewSource(filter)
                }
            }
            binding.flReviewSource.addView(root)
        }

        //Fluffie Filter
        binding.flFluffieFilter.removeAllViews()
        AppConst.fluffieFilter.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.selectedFluffieFilter.postValue(filter)
            }
            binding.flFluffieFilter.addView(root)
        }
    }

    fun onCloseClicked(view: View) {
        (activity as HomeActivity).onBackPressed()
    }

    fun onAcneProneClicked(view: View) {
        viewModel.acneProneSelected.postValue(viewModel.acneProneSelected.value?.not())
    }

    fun onClearAllClicked(view: View) {
        viewModel.selectedSkinType.postValue(null)
        viewModel.selectedAge.postValue(null)
        viewModel.acneProneSelected.postValue(false)
        viewModel.selectedAge.postValue(null)
        viewModel.selectedLocation.postValue(null)
        viewModel.selectedLocation.postValue(null)
        viewModel.selectedProductAspect.postValue(null)
        viewModel.selectedReviewSources.clear()
        viewModel.onReviewResourceChanged.postValue(true)
        viewModel.selectedFluffieFilter.postValue(null)
    }

    fun onShowProductsClicked(view: View) {
        (activity as HomeActivity).onBackPressed()
    }

    private fun selectTextView(tv: TextView) {
        tv.setBackgroundResource(R.drawable.rounded_rectangle_rose_fill)
        tv.setTextColor(colorWhite)
    }

    private fun resetTextView(tv: TextView) {
        tv.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        tv.setTextColor(colorGray)
    }

    private fun resetSkinTypes() {
        log("resetSkinTypes()")
        getTvs(binding.flSkinType).forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetProsOrCon() {
        log("resetProsOrCon()")
        getTvs(binding.flProsOrCon).forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetAges() {
        log("resetAges()")
        getTvs(binding.flAge).forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetLocations() {
        log("resetLocations()")
        getTvs(binding.flLocation).forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetProductAspects() {
        log("resetProductAspects()")
        getTvs(binding.flProductAspect).forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetReviewSource() {
        log("resetReviewSource()")
        getTvs(binding.flReviewSource).forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetFluffieFilter() {
        log("resetFluffieFilter()")
        getTvs(binding.flFluffieFilter).forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun getTvs(fl: FlexboxLayout): ArrayList<TextView> {
        val tvs = ArrayList<TextView>()
        fl.allViews.forEach {
            tvs.add(it.findViewById(R.id.tvOption))
        }
        return tvs
    }

    private fun setTextStyle(
        ds: TextPaint, color: Int, typeface: Typeface, shouldUnderline: Boolean
    ) {
        ds.textSize = 14.dpToPx
        ds.isUnderlineText = shouldUnderline
        ds.color = color
        ds.typeface = typeface
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}