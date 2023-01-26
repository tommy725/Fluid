package com.raycai.fluffie.ui.home.product.reviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.http.response.ProductReviewsResponse
import com.raycai.fluffie.ui.home.product.claims.ClaimsViewModel

class ReviewsViewModel() : ViewModel() {

    private val TAG = ClaimsViewModel::class.java.simpleName

    enum class FilterType {
        ALL, TRENDING, VIDEO, AUDIO
    }

    val selectedFilterType = MutableLiveData(FilterType.ALL)
    val reviewChanged = MutableLiveData<Boolean>()

    var reviews: ArrayList<ProductReviewsResponse.ProductReview> = ArrayList()

    fun onFilterSelected(ft: FilterType){
        if (ft != selectedFilterType.value){
            selectedFilterType.postValue(ft)
        }
    }

    fun initData(reviews: MutableList<ProductReviewsResponse.ProductReview>?) {
        selectedFilterType.postValue(FilterType.ALL)

        this.reviews.clear()
        this.reviews.addAll(reviews!!)
        reviewChanged.postValue(true)
    }


}