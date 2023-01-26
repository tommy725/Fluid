package com.raycai.fluffie.ui.home.browse

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dam.bestexpensetracker.util.AppLog
import com.google.android.material.tabs.TabLayout
import com.raycai.fluffie.HomeActivity
import com.raycai.fluffie.R
import com.raycai.fluffie.adapter.ProductAdapter
import com.raycai.fluffie.base.BaseFragment
import com.raycai.fluffie.databinding.FragmentBrowseBinding
import com.raycai.fluffie.http.Api
import com.raycai.fluffie.http.response.ProductListResponse
import com.raycai.fluffie.ui.bottomsheet.ProductCategoryBottomSheetDialog
import com.raycai.fluffie.util.AppConst
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BrowseFragment : BaseFragment(), ProductAdapter.Listener,
    ProductCategoryBottomSheetDialog.Listener {

    private val TAG = BrowseFragment::class.java.simpleName
    private lateinit var viewModel: BrowseViewModel
    private lateinit var binding: FragmentBrowseBinding

    //recyclerview
    private lateinit var adapter: ProductAdapter

    //Resources
    private lateinit var fontBold: Typeface
    private lateinit var fontLite: Typeface
    private var colorRose = 0
    private var colorWhite = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[BrowseViewModel::class.java]
        binding = FragmentBrowseBinding.inflate(inflater)
        binding.fragment = this
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setObservers()
        initData()
        return binding.root
    }

    private fun assignUserFilterFromHome() {
        viewModel.userFilter = (activity as HomeActivity).userFilter
    }

    private fun initData() {
        viewModel.selectedProductCategory.postValue((activity as HomeActivity).selectedProductCategory)
        viewModel.initData();
        assignUserFilterFromHome()

        colorRose = ContextCompat.getColor(requireContext(), R.color.rose)
        colorWhite = ContextCompat.getColor(requireContext(), R.color.white)
        fontBold = ResourcesCompat.getFont(requireContext(), R.font.roboto_bold)!!
        fontLite = ResourcesCompat.getFont(requireContext(), R.font.roboto_light)!!

        adapter = ProductAdapter(viewModel.products)
        adapter.listener = this
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.adapter = adapter

        loadUserFilters()
    }

    private fun loadUserFilters() {
        binding.layoutFilters.removeAllViews()

        binding.tvPersonalizeFilter.text = viewModel.userFilter?.skinType

        //Add selected filters
        if (viewModel.userFilter?.acneProneSelected!!) {
            addFilterToView(AppConst.USER_FILTER_ACNE_PRONE, AppConst.ACNE_PRONE, true)
        }

        if (viewModel.userFilter?.age != null) {
            addFilterToView(AppConst.USER_FILTER_AGE, viewModel.userFilter?.age!!, true)
        }

        if (!viewModel.userFilter?.selectedBenefits?.isEmpty()!!) {
            viewModel.userFilter?.selectedBenefits?.forEach {
                addFilterToView(AppConst.USER_FILTER_BEFEFITS, it, true)
            }
        }

        if (!viewModel.userFilter?.selectedConsistencies?.isEmpty()!!) {
            viewModel.userFilter?.selectedConsistencies?.forEach {
                addFilterToView(AppConst.USER_FILTER_CONSISTENCIES, it, true)
            }
        }

        if (!viewModel.userFilter?.selectedFragrances?.isEmpty()!!) {
            viewModel.userFilter?.selectedFragrances?.forEach {
                addFilterToView(AppConst.USER_FILTER_FRAGRANCE, it, true)
            }
        }

        //Add unselected filters
//        if (viewModel.userFilter?.age == null) {
//            AppConst.ages.forEach {
//                addFilterToView(AppConst.USER_FILTER_AGE, it, false)
//            }
//        }

        if (viewModel.selectedTopicsCount() < viewModel.maxTopicSelection) {
            if (viewModel.userFilter?.selectedBenefits?.isEmpty()!!) {
                AppConst.benefits.forEach {
                    addFilterToView(AppConst.USER_FILTER_BEFEFITS, it, false)
                }
            } else {
                AppConst.benefits.forEach {
                    if (!viewModel.isBenefitExists(it)) {
                        addFilterToView(AppConst.USER_FILTER_BEFEFITS, it, false)
                    }
                }
            }

            if (viewModel.userFilter?.selectedConsistencies?.isEmpty()!!) {
                AppConst.productConsistencies.forEach {
                    addFilterToView(AppConst.USER_FILTER_CONSISTENCIES, it, false)
                }
            } else {
                AppConst.productConsistencies.forEach {
                    if (!viewModel.isConsistencyExists(it)) {
                        addFilterToView(AppConst.USER_FILTER_CONSISTENCIES, it, false)
                    }
                }
            }

            if (viewModel.userFilter?.selectedFragrances?.isEmpty()!!) {
                AppConst.fragrance.forEach {
                    addFilterToView(AppConst.USER_FILTER_FRAGRANCE, it, false)
                }
            } else {
                AppConst.fragrance.forEach {
                    if (!viewModel.isFragranceExists(it)) {
                        addFilterToView(AppConst.USER_FILTER_FRAGRANCE, it, false)
                    }
                }
            }
        }
    }

    private fun addFilterToView(category: String, filter: String, isSelected: Boolean) {
        val root = layoutInflater.inflate(R.layout.tv_filter_display, null)
        root.tag = category
        val tv = root.findViewById<TextView>(R.id.tvOption)
        tv.text = filter

        if (isSelected) {
            tv.setBackgroundResource(R.drawable.rounded_rectangle_rose_fill)
        } else {
            tv.setBackgroundResource(R.drawable.rounded_rectangle_rose)
        }

        root.setOnClickListener {
            when (category) {
                AppConst.USER_FILTER_AGE -> {
                    viewModel.onUserFilterAgeFilterClicked(filter)
                }
                AppConst.USER_FILTER_BEFEFITS -> {
                    viewModel.onUserFilterBenefitClicked(filter)
                }
                AppConst.USER_FILTER_CONSISTENCIES -> {
                    viewModel.onUserFilterProductConsistencyClicked(filter)
                }
                AppConst.USER_FILTER_FRAGRANCE -> {
                    viewModel.onUserFilterFragranceClicked(filter)
                }
                AppConst.USER_FILTER_ACNE_PRONE -> {
                    viewModel.onUserFilterAceProneClicked(filter)
                }
            }
        }

        binding.layoutFilters.addView(root)
    }

    private fun setObservers() {
        viewModel.selectedProductCategory.observeForever {
            binding.tvProductCategory.text = it.master_category

            viewModel.tabs.clear()
            viewModel.tabs.add("All")
            if (viewModel.selectedProductCategory.value?.refind_category != null){
                viewModel.selectedProductCategory.value?.refind_category!!.forEach {
                    viewModel.tabs.add(it.refined_category)
                }
            }
            viewModel.tabsChanged.postValue(true)
        }

        viewModel.productsChanged.observeForever {
            adapter.notifyDataSetChanged()
        }

        viewModel.tabsChanged.observeForever {
            binding.tabLayout.removeAllTabs()
            log("tabs changed.")
            viewModel.tabs.forEachIndexed { index, it ->
                log("tab : $it")
                val tab: TabLayout.Tab = binding.tabLayout.newTab()
                tab.text = it
                binding.tabLayout.addTab(tab, index == 0)
            }
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    getProductList(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewModel.userFilterSelectionChanged.observeForever {
            (activity as HomeActivity).userFilter = viewModel.userFilter
            loadUserFilters()
        }
    }


    override fun onProductCategoryClicked(productCategory: String) {
        (activity as HomeActivity).categoryList.forEach {
            if (it.master_category.equals(productCategory)) {
                (activity as HomeActivity).selectedProductCategory = it
            }
        }

        viewModel.selectedProductCategory.postValue((activity as HomeActivity).selectedProductCategory)
    }

    override fun onProductClicked(p: ProductListResponse.ProductDetail) {
        (activity as HomeActivity).selectedProductId = p.id
        (activity as HomeActivity).loadProductFragment(true)
    }

    fun onPersonalizeFilterClicked(view: View) {
        (activity as HomeActivity).loadUserFilterFragment()
    }

    fun onProductFilterClicked(view: View) {
        (activity as HomeActivity).loadProductFilterFragment()
    }

    fun onChangeProductCategoryClicked(view: View) {
        val dialog = ProductCategoryBottomSheetDialog()
        dialog.show(
            childFragmentManager,
            ProductCategoryBottomSheetDialog::class.java.canonicalName
        )
        dialog.listener = this
    }

    fun onSearch(view: View) {
        (activity as HomeActivity).loadProductSearchFragment(true)
    }

    fun onBackPressed(view: View) {

    }

    private fun setTextStyle(
        ds: TextPaint,
        color: Int,
        typeface: Typeface,
        shouldUnderline: Boolean
    ) {
        ds.textSize = 13.dpToPx
        ds.isUnderlineText = shouldUnderline
        ds.color = color
        ds.typeface = typeface
    }

    private fun log(msg: String) {
        AppLog.log(TAG, msg)
    }

    private fun getProductList(subCategoryPosition: Int) {
        showProgress()
        Api().ApiClient().getProductList().enqueue(object : Callback<ProductListResponse> {
            override fun onResponse(
                call: Call<ProductListResponse>?,
                response: Response<ProductListResponse>?
            ) {
                hideProgress()

                if (response!!.body().status) {
//                    var filteredList: ArrayList<ProductListResponse.ProductDetail> = ArrayList()
//                    response!!.body().data!!.forEach {
//                        if (it.category)
//                    }
                    showProducts(response!!.body().data!!)
                } else
                    println("API parse failed")
            }

            override fun onFailure(call: Call<ProductListResponse>?, t: Throwable?) {
                println(t!!.message)
                hideProgress()
            }
        })
    }

    private fun showProducts(list: MutableList<ProductListResponse.ProductDetail>) {

        // <editor-fold defaultstate="collapsed" desc="Filter Text Highlight">
        val wordtoSpan: Spannable =
            SpannableString("We found ${list.count()} moisturisers that have the most reviews that mention reduced fine lines, good for dry skin and hydrated skin.")

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
                setTextStyle(ds, colorWhite, fontLite, true)
            }
        }

        val txt3: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorRose, fontBold, false)
            }
        }

        val txt4: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                setTextStyle(ds, colorRose, fontBold, false)
            }
        }

        val word1 = "${list.count()} moisturisers"
        val txt1Start = wordtoSpan.indexOf(word1)
        val txt1End = txt1Start + word1.length
        wordtoSpan.setSpan(txt1, txt1Start, txt1End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val word2 = "most reviews"
        val txt2Start = wordtoSpan.indexOf(word2)
        val txt2End = txt2Start + word2.length
        wordtoSpan.setSpan(txt2, txt2Start, txt2End, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        val word3 = "reduced fine lines, good for dry skin"
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

        viewModel.products.clear()
        viewModel.products.addAll(list)
        viewModel.productsChanged.postValue(true)
    }
}

