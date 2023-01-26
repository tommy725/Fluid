package com.raycai.fluffie.ui.home.product.prosorcon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.data.model.Con2
import com.raycai.fluffie.data.model.Product
import com.raycai.fluffie.data.model.Pros
import com.raycai.fluffie.http.response.ProductDetailResponse
import com.raycai.fluffie.ui.home.product.claims.ClaimsViewModel
import com.raycai.fluffie.util.AppConst

class ProsOrConViewModel() : ViewModel() {

    private val TAG = ClaimsViewModel::class.java.simpleName

    val tabsChanged = MutableLiveData<Boolean>()
    val selectedProduct = MutableLiveData<ProductDetailResponse.ProductDetail?>()
    val selectedPros = MutableLiveData<Pros?>()
    val selectedCon = MutableLiveData<Con2?>()

    val minReviews = 3
    var isShowingMoreReviews = false
    val reviews = AppConst.reviews

    val minPros = 5
    var isShowingMore = false
    val prosList = AppConst.prosList
    val conList = AppConst.consList

    val tabs = ArrayList<String>()

    fun initData() {
        tabs.add("Brighter skin (33)")
        tabs.add("Evened tone (26)")
        tabs.add("Clearer skin (5)")
        tabsChanged.postValue(true)
    }


}