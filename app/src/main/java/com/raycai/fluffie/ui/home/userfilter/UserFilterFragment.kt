package com.raycai.fluffie.ui.home.userfilter

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
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentUserFilterBinding
import com.raycai.fluffie.util.AppConst

class UserFilterFragment : BaseFragment() {

    private val TAG = UserFilterFragment::class.java.simpleName
    private lateinit var viewModel: UserFilterViewModel
    private lateinit var binding: FragmentUserFilterBinding

    //Resources
    private lateinit var fontBold: Typeface
    private lateinit var fontLite: Typeface
    private var colorRose = 0
    private var colorWhite = 0
    private var colorGray = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[UserFilterViewModel::class.java]
        binding = FragmentUserFilterBinding.inflate(inflater)
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

        viewModel.selectedTopicsCount.observeForever {
            binding.tvTopicsCount.text = "$it"
        }

        viewModel.selectedSkinType.observeForever { skinType ->
            resetSkinTypes()
            if (skinType != null) {
                getSkinTypeTvs().forEach {
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

        viewModel.selectedAge.observeForever { age ->
            resetAges()
            if (age != null) {
                getAgeTvs().forEach {
                    if (it.text.toString() == age) {
                        selectTextView(it)
                    }
                }
            }
        }

        viewModel.onBenefitDataChanged.observeForever {
            resetBenefits()
            getBenefitTvs().forEach { tv ->
                viewModel.selectedBenefits.forEach {
                    if (tv.text.toString() == it) {
                        selectTextView(tv)
                    }
                }
            }
        }

        viewModel.onPCDataChanged.observeForever {
            resetPCs()
            getPCTvs().forEach { tv ->
                viewModel.selectedConsistencies.forEach {
                    if (tv.text.toString() == it) {
                        selectTextView(tv)
                    }
                }
            }
        }

        viewModel.onFragranceChanged.observeForever {
            resetFragrance()
            getFragranceTvs().forEach { tv ->
                viewModel.selectedFragrances.forEach {
                    if (tv.text.toString() == it) {
                        selectTextView(tv)
                    }
                }
            }
        }
    }

    private fun initData() {
        viewModel.setUserFilterData((activity as HomeActivity).userFilter)

        (activity as HomeActivity).setBottomNavigationVisibility(false)
        viewModel.initData()
        colorRose = ContextCompat.getColor(requireContext(), R.color.rose)
        colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        colorGray = ContextCompat.getColor(requireContext(), R.color.gray)
        fontBold = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)!!
        fontLite = ResourcesCompat.getFont(requireContext(), R.font.roboto_light)!!

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

        //Benefits
        binding.flBenefits.removeAllViews()
        AppConst.benefits.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.benefitSelected(filter)
            }
            binding.flBenefits.addView(root)
        }

        //Product consistency
        binding.flProductConsistency.removeAllViews()
        AppConst.productConsistencies.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.pcSelected(filter)
            }
            binding.flProductConsistency.addView(root)
        }

        //Fragrance
        binding.flFragrance.removeAllViews()
        AppConst.fragrance.forEach { filter ->
            val root = layoutInflater.inflate(R.layout.tv_filter_option, null)
            val tv = root.findViewById<TextView>(R.id.tvOption)
            tv.text = filter
            tv.setOnClickListener {
                viewModel.fragranceSelected(filter)
            }
            binding.flFragrance.addView(root)
        }

        // <editor-fold defaultstate="collapsed" desc="Filter Text Highlight">
        val wordtoSpan: Spannable =
            SpannableString("Filter products based on its key topics and benefits.")

        val txt1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorRose, fontBold, false)
            }
        }

        val txt2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorRose, fontBold, false)
            }
        }

        val word1 = "key topics"
        val txt1Start = wordtoSpan.indexOf(word1)
        val txt1End = txt1Start + word1.length
        wordtoSpan.setSpan(txt1, txt1Start, txt1End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val word2 = "benefits"
        val txt2Start = wordtoSpan.indexOf(word2)
        val txt2End = txt2Start + word2.length
        wordtoSpan.setSpan(txt2, txt2Start, txt2End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.tvTitle.text = wordtoSpan
        binding.tvTitle.movementMethod = LinkMovementMethod.getInstance()
        binding.tvTitle.highlightColor = ContextCompat.getColor(requireContext(), R.color.rose)
        // </editor-fold>
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
        viewModel.selectedBenefits.clear()
        viewModel.onBenefitDataChanged.postValue(true)
        viewModel.selectedConsistencies.clear()
        viewModel.onPCDataChanged.postValue(true)
        viewModel.selectedFragrances.clear()
        viewModel.onFragranceChanged.postValue(true)
    }

    fun onShowProductsClicked(view: View) {
        val uf = viewModel.createUserFilterObj()
        (activity as HomeActivity).userFilter = uf
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
        getSkinTypeTvs().forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetAges() {
        log("resetAges()")
        getAgeTvs().forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetBenefits() {
        log("resetBenefits()")
        getBenefitTvs().forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetPCs() {
        log("resetPCs()")
        getPCTvs().forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetFragrance() {
        log("resetFragrance()")
        getFragranceTvs().forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun getSkinTypeTvs(): ArrayList<TextView> {
        val tvs = ArrayList<TextView>()
        binding.flSkinType.allViews.forEach {
            tvs.add(it.findViewById(R.id.tvOption))
        }
        return tvs
    }

    private fun getAgeTvs(): ArrayList<TextView> {
        val tvs = ArrayList<TextView>()
        binding.flAge.allViews.forEach {
            tvs.add(it.findViewById(R.id.tvOption))
        }
        return tvs
    }

    private fun getBenefitTvs(): ArrayList<TextView> {
        val tvs = ArrayList<TextView>()
        binding.flBenefits.allViews.forEach {
            tvs.add(it.findViewById(R.id.tvOption))
        }
        return tvs
    }

    private fun getPCTvs(): ArrayList<TextView> {
        val tvs = ArrayList<TextView>()
        binding.flProductConsistency.allViews.forEach {
            tvs.add(it.findViewById(R.id.tvOption))
        }
        return tvs
    }

    private fun getFragranceTvs(): ArrayList<TextView> {
        val tvs = ArrayList<TextView>()
        binding.flFragrance.allViews.forEach {
            tvs.add(it.findViewById(R.id.tvOption))
        }
        return tvs
    }

    private fun setTextStyle(
        ds: TextPaint,
        color: Int,
        typeface: Typeface,
        shouldUnderline: Boolean
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