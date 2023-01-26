package com.raycai.fluffie.ui.home.product.prosorcon.filter

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raycai.fluffie.data.model.UserFilter
import com.raycai.fluffie.ui.home.product.claims.ClaimsViewModel
import com.raycai.fluffie.util.AppConst

class ProsOrConFilterViewModel() : ViewModel() {

    private val TAG = ClaimsViewModel::class.java.simpleName

    val prosOrCon = MutableLiveData<String?>()
    val selectedSkinType = MutableLiveData("\uD83C\uDF35 Dry")
    val acneProneSelected = MutableLiveData(false)
    val selectedAge = MutableLiveData<String?>()
    val selectedLocation = MutableLiveData<String?>()
    val selectedProductAspect = MutableLiveData<String?>()
    val selectedFluffieFilter = MutableLiveData<String?>()

    val selectedReviewSources = ArrayList<String>()
    val onReviewResourceChanged = MutableLiveData(false)

    val btnActionEnable = MutableLiveData(false)

    fun initData() {
        prosOrCon.value = "Pros"
        selectedSkinType.observeForever {
            btnActionEnable.postValue(!TextUtils.isEmpty(it))
        }
    }

    fun selectAllReviewResource(){
        selectedReviewSources.clear()
        AppConst.reviewSource.forEach {
            if (it != "Select all"){
                selectedReviewSources.add(it)
            }
        }

        onReviewResourceChanged.postValue(true)
    }

    fun selectedReviewSource(s: String) {
        if (isReviewSourceExists(s)) {
            selectedReviewSources.remove(s)
        } else {
            selectedReviewSources.add(s)
        }
        onReviewResourceChanged.postValue(true)
    }

    private fun isReviewSourceExists(b: String): Boolean {
        selectedReviewSources.forEach {
            if (it == b) {
                return true
            }
        }
        return false
    }

}