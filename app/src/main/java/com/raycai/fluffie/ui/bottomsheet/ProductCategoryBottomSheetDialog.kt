package com.raycai.fluffie.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.databinding.BottomSheetProductCategoryBinding


class ProductCategoryBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetProductCategoryBinding

    var listener: Listener? = null

    private var colorGray = 0
    private var colorRose = 0
    private var selectedCategory = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetProductCategoryBinding.inflate(layoutInflater)
        binding.dialog = this
        binding.lifecycleOwner = this
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        colorGray = ContextCompat.getColor(requireContext(), R.color.gray)
        colorRose = ContextCompat.getColor(requireContext(), R.color.rose)

        val selectedCategory = (activity as HomeActivity).selectedProductCategory
        if (selectedCategory == null) {
            onTv1Clicked(binding.tv1)
        } else {
            var ar = arrayListOf(
                binding.tv1, binding.tv2, binding.tv3, binding.tv4, binding.tv5,
                binding.tv6, binding.tv7, binding.tv8, binding.tv9
            )

            ar.forEach {
                if (it.text == selectedCategory.master_category){
                    selectCategory(it)
                }
            }
        }
    }

    fun onTv1Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv2Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv3Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv4Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv5Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv6Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv7Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv8Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onTv9Clicked(view: View) {
        resetCategories()
        selectCategory((view as TextView))
        selectedCategory = view.text as String
    }

    fun onBtnShowClicked(view: View) {
        dismiss()
        listener?.onProductCategoryClicked(selectedCategory)
    }

    private fun selectCategory(tv: TextView) {
        binding.btnShow.text = "Show ${tv.text.toString().lowercase()}"
        tv.setBackgroundResource(R.drawable.rounded_rectangle_rose)
        tv.setTextColor(colorRose)
    }

    private fun resetCategories() {
        binding.tv1.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv2.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv3.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv4.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv5.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv6.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv7.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv8.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv9.setBackgroundResource(R.drawable.rounded_rectangle_gray)
        binding.tv1.setTextColor(colorGray)
        binding.tv2.setTextColor(colorGray)
        binding.tv3.setTextColor(colorGray)
        binding.tv4.setTextColor(colorGray)
        binding.tv5.setTextColor(colorGray)
        binding.tv6.setTextColor(colorGray)
        binding.tv7.setTextColor(colorGray)
        binding.tv8.setTextColor(colorGray)
        binding.tv9.setTextColor(colorGray)
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialogTheme
    }

    interface Listener {
        fun onProductCategoryClicked(productCategory: String)
    }
}