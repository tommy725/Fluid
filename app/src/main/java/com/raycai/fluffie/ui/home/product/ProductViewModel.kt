package com.raycai.fluffie.ui.home.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.http.response.ProductDetailResponse

class ProductViewModel() : ViewModel() {

    private val TAG = ProductViewModel::class.java.simpleName

    val tabsChanged = MutableLiveData<Boolean>()
    val product = MutableLiveData<ProductDetailResponse.ProductDetail>()

    val tabs = ArrayList<String>()

    fun initData() {
        tabs.add("Claims")
        tabs.add("Summaries")
        tabs.add("Our reviews")
        tabsChanged.postValue(true)
    }


}