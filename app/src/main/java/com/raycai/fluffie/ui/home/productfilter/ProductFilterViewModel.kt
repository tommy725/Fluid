package com.raycai.fluffie.ui.home.productfilter

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductFilterViewModel() : ViewModel() {

    private val TAG = ProductFilterViewModel::class.java.simpleName

    val onBenefitClicked = MutableLiveData<String>()
    val onReviewSourceClicked = MutableLiveData<String>()

    //view notifiers
    val onBenefitDataChanged = MutableLiveData<String>()
    val onReviewDataChanged = MutableLiveData<String>()

    val selectedBenefits = ArrayList<String>()
    val selectedReviewSources = ArrayList<String>()

    fun initData() {
        onBenefitClicked.observeForever {
            if (benefitExists(it)) {
                selectedBenefits.remove(it)
            } else {
                selectedBenefits.add(it)
            }
            onBenefitDataChanged.postValue(it)
        }

        onReviewSourceClicked.observeForever {
            if (reviewExists(it)) {
                selectedReviewSources.remove(it)
            } else {
                selectedReviewSources.add(it)
            }
            onReviewDataChanged.postValue(it)
        }
    }


    private fun reviewExists(b: String): Boolean {
        selectedReviewSources.forEach {
            if (it == b) {
                return true
            }
        }

        return false
    }

    private fun benefitExists(b: String): Boolean {
        selectedBenefits.forEach {
            if (it == b) {
                return true
            }
        }

        return false
    }
}