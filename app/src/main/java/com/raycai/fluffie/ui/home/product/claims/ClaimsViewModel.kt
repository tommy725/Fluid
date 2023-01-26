package com.raycai.fluffie.ui.home.product.claims

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.raycai.fluffie.http.response.ProductDetailResponse

class ClaimsViewModel() : ViewModel() {

    private val TAG = ClaimsViewModel::class.java.simpleName

    val isShowingMore = MutableLiveData<Boolean>()
    val selectedProduct = MutableLiveData<ProductDetailResponse.ProductDetail?>()

    fun onShowMoreClicked() {
        isShowingMore.postValue(!isShowingMore.value!!)
    }

    fun initData() {
        isShowingMore.postValue(false)
    }


}