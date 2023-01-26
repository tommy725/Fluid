package com.raycai.fluffie.ui.home.productsearch

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
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.dam.bestexpensetracker.util.AppLog
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.data.model.Product
import com.raycai.fluffie.databinding.FragmentProductSearchBinding
import com.raycai.fluffie.http.Api
import com.raycai.fluffie.http.response.CategoryListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductSearchFragment : BaseFragment() {

    private val TAG = ProductSearchFragment::class.java.simpleName
    private lateinit var viewModel: ProductSearchViewModel
    private lateinit var binding: FragmentProductSearchBinding

    //Resources
    private lateinit var fontBold: Typeface
    private lateinit var fontMedium: Typeface
    private lateinit var fontLite: Typeface
    private var colorRose = 0
    private var colorWhite = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ProductSearchViewModel::class.java]
        binding = FragmentProductSearchBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        initData()
        return binding.root
    }

    private fun initData() {
        colorRose = ContextCompat.getColor(requireContext(), R.color.rose)
        colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        fontBold = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)!!
        fontMedium = ResourcesCompat.getFont(requireContext(), R.font.roboto_medium)!!
        fontLite = ResourcesCompat.getFont(requireContext(), R.font.roboto_light)!!

        // <editor-fold defaultstate="collapsed" desc="Filter Text Highlight">
        val wordtoSpan: Spannable =
            SpannableString("We found 812 skincare products that have the most reviews mentioning benefits of reduced wrinkles, good for dry skin and hydrated skin.")

        val txt1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorRose, fontMedium, false)
            }
        }

        val txt2: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorWhite, fontMedium, true)
            }
        }

        val txt3: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorRose, fontMedium, false)
            }
        }

        val txt4: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorRose, fontMedium, false)
            }
        }

        val word1 = "812 skincare products"
        val txt1Start = wordtoSpan.indexOf(word1)
        val txt1End = txt1Start + word1.length
        wordtoSpan.setSpan(txt1, txt1Start, txt1End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val word2 = "most reviews"
        val txt2Start = wordtoSpan.indexOf(word2)
        val txt2End = txt2Start + word2.length
        wordtoSpan.setSpan(txt2, txt2Start, txt2End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val word3 = "reduced wrinkles, good for dry skin"
        val txt3Start = wordtoSpan.indexOf(word3)
        val txt3End = txt3Start + word3.length
        wordtoSpan.setSpan(txt3, txt3Start, txt3End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val word4 = "hydrated skin."
        val txt4Start = wordtoSpan.indexOf(word4)
        val txt4End = txt4Start + word4.length
        wordtoSpan.setSpan(txt4, txt4Start, txt4End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.filterInfo.text = wordtoSpan
        binding.filterInfo.movementMethod = LinkMovementMethod.getInstance()
        binding.filterInfo.highlightColor = ContextCompat.getColor(requireContext(), R.color.white)
        // </editor-fold>

        binding.l1.findViewById<CardView>(R.id.cvRoot).setOnClickListener {
            loadProductFragment("")
        }

        binding.l2.findViewById<CardView>(R.id.cvRoot).setOnClickListener {
            loadProductFragment("")
        }

        binding.l3.findViewById<CardView>(R.id.cvRoot).setOnClickListener {
            loadProductFragment("")
        }

        binding.l4.findViewById<CardView>(R.id.cvRoot).setOnClickListener {
            loadProductFragment("")
        }

        //load temp data
        getCategoryList()
    }

    private fun loadProductFragment(productId: String) {
        (activity as HomeActivity).selectedProductId = productId
        (activity as HomeActivity).loadProductFragment(true)
    }

    private fun addView(layout: LinearLayout, p: Product) {

    }

    private fun loadProductSearchFragment(filter: String?) {
        if (filter != null) {
            (activity as HomeActivity).categoryList.forEach {
                if (it.master_category.equals(filter)) {
                    (activity as HomeActivity).selectedProductCategory = it
                }
            }

            if ((activity as HomeActivity).selectedProductCategory != null)
                (activity as HomeActivity).loadBrowseFragment(true)
        }
    }

    fun onFilterByClicked(view: View) {
        loadProductSearchFragment(null)
    }

    fun onFilterByMoisturisersClicked(view: View) {
        loadProductSearchFragment("Moisturisers")
    }

    fun onFilterByTreatmentsClicked(view: View) {
        loadProductSearchFragment("Treatments")
    }

    fun onFilterByCleansersClicked(view: View) {
        loadProductSearchFragment("Cleansers")
    }

    fun onFilterBySunscreensClicked(view: View) {
        loadProductSearchFragment("Sunscreens")
    }

    fun onFilterByMasksClicked(view: View) {
        loadProductSearchFragment("Masks")
    }

    fun onFilterByLipCareClicked(view: View) {
        loadProductSearchFragment("Lip Care")
    }

    fun onFilterByMakeupClicked(view: View) {
        loadProductSearchFragment("Makeup")
    }

    fun onFilterByDevicesClicked(view: View) {
        loadProductSearchFragment("Devices")
    }

    fun onFilterByOtherClicked(view: View) {
        loadProductSearchFragment("Other")
    }

    fun onTitleMoisturisersClicked(view: View) {

    }

    fun onTitleTreatmentsClicked(view: View) {

    }

    fun onBackPressed(view: View) {
        onBackPressed()
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

    private fun getCategoryList() {
        showProgress()
        Api().ApiClient().getCategoryList().enqueue(object : Callback<CategoryListResponse> {
            override fun onResponse(
                call: Call<CategoryListResponse>?,
                response: Response<CategoryListResponse>?
            ) {
                hideProgress()

                if (response!!.body().status) {
                    (activity as HomeActivity).categoryList.clear()
                    (activity as HomeActivity).categoryList.addAll(response.body().data!!)
                } else
                    println("API parse failed")
            }

            override fun onFailure(call: Call<CategoryListResponse>?, t: Throwable?) {

                println("API execute failed")
                hideProgress()
            }
        })
    }

}