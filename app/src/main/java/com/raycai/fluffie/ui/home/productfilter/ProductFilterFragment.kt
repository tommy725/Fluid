package com.raycai.fluffie.ui.home.productfilter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.google.android.material.resources.TextAppearance
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.tooltip.TooltipDrawable
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentProductFilterBinding
import java.lang.reflect.Field


class ProductFilterFragment : BaseFragment() {

    private val TAG = ProductFilterFragment::class.java.simpleName
    private lateinit var viewModel: ProductFilterViewModel
    private lateinit var binding: FragmentProductFilterBinding

    private var colorGray = 0
    private var colorRed = 0
    private var colorWhite = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProductFilterViewModel::class.java]
        binding = FragmentProductFilterBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        initData()
        return binding.root
    }

    private fun setObservers() {
        viewModel.onBenefitDataChanged.observeForever {
            resetBenefits()
            getBenefitTvs().forEach { tv ->
                viewModel.selectedBenefits.forEach {
                    if (it == tv.text.toString()) {
                        selectTextView(tv)
                    }
                }
            }
        }

        viewModel.onReviewDataChanged.observeForever {
            resetReviewSources()
            getReviewSourceTvs().forEach { tv ->
                viewModel.selectedReviewSources.forEach {
                    if (it == tv.text.toString()) {
                        selectTextView(tv)
                    }
                }
            }
        }
    }

    private fun initData() {
        (activity as HomeActivity).setBottomNavigationVisibility(false)
        viewModel.initData()
        colorGray = ContextCompat.getColor(requireContext(), R.color.gray)
        colorRed = ContextCompat.getColor(requireContext(), R.color.red)
        colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        binding.rangeSlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter "$ ${value.toInt()}"
        }

        binding.rangeSlider.thumbRadius = 12.dpToPx.toInt()
        binding.rangeSlider.thumbStrokeWidth = 1.dpToPx
        binding.rangeSlider.thumbStrokeColor = resources.getColorStateList(R.color.rose)
        binding.rangeSlider.trackHeight = 2.dpToPx.toInt()

        changeSliderTypeFace(binding.rangeSlider, R.style.CustomTooltipTextAppearance)
    }

    @SuppressLint("RestrictedApi")
    fun changeSliderTypeFace(slider: RangeSlider, @StyleRes textAppearanceStyleResId: Int) {
        try {
            val baseSliderCls: Class<*>? = Slider::class.java.superclass
            if (baseSliderCls != null) {
                val privateLabelsField: Field = baseSliderCls.getDeclaredField("labels")
                privateLabelsField.setAccessible(true)
                val tooltipDrawableList = privateLabelsField.get(slider) as List<TooltipDrawable>
                if (tooltipDrawableList != null && tooltipDrawableList.size > 0) {
                    for (tooltipDrawable in tooltipDrawableList) {
                        tooltipDrawable.textAppearance =
                            TextAppearance(slider.context, textAppearanceStyleResId)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onCloseClicked(view: View) {
        (activity as HomeActivity).onBackPressed()
    }

    fun onSelectAllBenefitClicked(view: View) {
        viewModel.selectedBenefits.clear()
        getBenefitTvs().forEach {
            viewModel.selectedBenefits.add(it.text.toString())
            selectTextView(it)
        }
    }

    fun onBenefit2Clicked(view: View) {
        viewModel.onBenefitClicked.postValue((view as TextView).text.toString())
    }

    fun onBenefit3Clicked(view: View) {
        viewModel.onBenefitClicked.postValue((view as TextView).text.toString())
    }

    fun onBenefit4Clicked(view: View) {
        viewModel.onBenefitClicked.postValue((view as TextView).text.toString())
    }

    fun onBenefit5Clicked(view: View) {
        viewModel.onBenefitClicked.postValue((view as TextView).text.toString())
    }

    fun onBenefit6Clicked(view: View) {
        viewModel.onBenefitClicked.postValue((view as TextView).text.toString())
    }

    fun onBenefit7Clicked(view: View) {
        viewModel.onBenefitClicked.postValue((view as TextView).text.toString())
    }

    fun onSelectAllReviewSources(view: View) {
        viewModel.selectedReviewSources.clear()
        getReviewSourceTvs().forEach {
            viewModel.selectedReviewSources.add(it.text.toString())
            selectTextView(it)
        }
    }

    fun onRs2Clicked(view: View) {
        viewModel.onReviewSourceClicked.postValue((view as TextView).text.toString())
    }

    fun onRs3Clicked(view: View) {
        viewModel.onReviewSourceClicked.postValue((view as TextView).text.toString())
    }

    fun onRs4Clicked(view: View) {
        viewModel.onReviewSourceClicked.postValue((view as TextView).text.toString())
    }

    fun onRs5Clicked(view: View) {
        viewModel.onReviewSourceClicked.postValue((view as TextView).text.toString())
    }

    fun onRs6Clicked(view: View) {
        viewModel.onReviewSourceClicked.postValue((view as TextView).text.toString())
    }

    fun onRs7Clicked(view: View) {
        viewModel.onReviewSourceClicked.postValue((view as TextView).text.toString())
    }

    fun onClearAllClicked(view: View) {
        viewModel.selectedBenefits.clear()
        viewModel.selectedReviewSources.clear()
        resetBenefits()
        resetReviewSources()
        binding.rangeSlider.setValues(5f, 500f)
    }

    fun onShowProductsClicked(view: View) {
        (activity as HomeActivity).onBackPressed()
    }

    private fun selectTextView(tv: TextView) {
        tv.setBackgroundResource(R.drawable.rounded_rectangle_rose_fill)
        tv.setTextColor(colorWhite)
    }

    private fun resetBenefits() {
        getBenefitTvs().forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun resetReviewSources() {
        getReviewSourceTvs().forEach {
            it.setBackgroundResource(R.drawable.rounded_rectangle_gray)
            it.setTextColor(colorGray)
        }
    }

    private fun getBenefitTvs(): ArrayList<TextView> {
        return arrayListOf(
            binding.tvBenefit2,
            binding.tvBenefit3,
            binding.tvBenefit4,
            binding.tvBenefit5,
            binding.tvBenefit6,
            binding.tvBenefit7
        )
    }

    private fun getReviewSourceTvs(): ArrayList<TextView> {
        return arrayListOf(
            binding.tvRS2,
            binding.tvRS3,
            binding.tvRS4,
            binding.tvRS5,
            binding.tvRS6,
            binding.tvRS7
        )
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

}